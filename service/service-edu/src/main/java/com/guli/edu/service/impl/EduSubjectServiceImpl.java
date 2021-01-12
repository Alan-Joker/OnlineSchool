package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.edu.entity.EduSubject;
import com.guli.edu.entity.vo.OneSubject;
import com.guli.edu.entity.vo.TwoSubject;
import com.guli.edu.mapper.EduSubjectMapper;
import com.guli.edu.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Alan_
 * @since 2021-01-10
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 导入Excel到数据库过程的错误信息
     *
     * @param file
     * @return
     */
    @Override
    public List<String> importExcel(MultipartFile file) {

        //存储错误信息集合
        List<String> meg = new ArrayList<>();
        try {
            //1.获取文件流
            InputStream inputStream = file.getInputStream();
            //2.根据流创建一个workBook
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            //3.获取sheet，getSheetAt(0);
            XSSFSheet sheet = workbook.getSheetAt(0);
            //4.根据sheet获取行数
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum <= 1) {
                meg.add("请填写数据");
                return meg;
            }
            //5.遍历行
            for (int rowNum = 1; rowNum < lastRowNum; rowNum++) {
                XSSFRow row = sheet.getRow(rowNum);
                //6.获取每一行中的列
                XSSFCell cell = row.getCell(0);
                if (cell == null) {
                    meg.add("第" + rowNum + "行第一列为空");
                    continue;
                }
                //获取每一列的内容
                String cellValue = cell.getStringCellValue();
                if (StringUtils.isEmpty(cellValue)) {
                    meg.add("第" + rowNum + "行第一列为空");
                    continue;
                }
                //7.判断列是否存在，存在获取列的数据
                EduSubject subject = this.selectSubjectByName(cellValue);
                String pid = null;
                //8.把这个第一列中的数据(一级分类)保存到数据库
                if (subject == null) {
                    //9.在保存之前判断此一级分类中是否存在，如果存在，不添加，如果不存在，在保存
                    EduSubject su = new EduSubject();
                    su.setTitle(cellValue);
                    su.setParentId("0");
                    su.setSort(0);
                    baseMapper.insert(su);
                    //新增数据后会把自动生成的主键封装给Bean
                    pid = su.getId();
                } else {
                    pid = subject.getId();
                }
                //10.在获取每一行的第二列
                XSSFCell cell1 = row.getCell(1);
                //11.获取第二列中的数据(二级分类)
                if (cell1 == null) {
                    meg.add("第" + rowNum + "行第二列为空");
                    continue;
                }
                String stringCellValue = cell1.getStringCellValue();
                if (StringUtils.isEmpty(stringCellValue)) {
                    meg.add("第" + rowNum + "行第二列为空");
                    continue;
                }
                //12.判断一级分类中是否存在此二级分类
                EduSubject subject2 = this.selectSubjectByNameAndParentId(stringCellValue, pid);
                //13.如果一级分类中有此二级分类:不保存
                if (subject2 == null) {
                    EduSubject su = new EduSubject();
                    su.setTitle(stringCellValue);
                    su.setParentId(pid);
                    su.setSort(0);
                    baseMapper.insert(su);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return meg;
    }


    /**
     * 根据二级分类名称和parentID查询是否存subject
     *
     * @param stringCellValue
     * @param pid
     * @return
     */
    private EduSubject selectSubjectByNameAndParentId(String stringCellValue, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", stringCellValue);
        wrapper.eq("parent_id", pid);
        EduSubject subject = baseMapper.selectOne(wrapper);
        return subject;
    }

    /**
     * 根据课程一级分类名称查询是否存在
     *
     * @param cellValue
     * @return
     */
    private EduSubject selectSubjectByName(String cellValue) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", cellValue);
        wrapper.eq("parent_id", "0");
        EduSubject subject = baseMapper.selectOne(wrapper);
        return subject;
    }


    /**
     * 获取课程分类
     *
     * @return
     */
    @Override
    public List<OneSubject> getTree() {
        //创建一个集合存放OneSubject
        List<OneSubject> oneSubject = new ArrayList<>();
        //获取一级分类列表
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", "0");
        List<EduSubject> eduSubjects = baseMapper.selectList(wrapper);
        //遍历一级分类列表
        for (EduSubject subject : eduSubjects) {
            //将一级分类的数据复制到OneSubject
            OneSubject oneSubject1 = new OneSubject();
            BeanUtils.copyProperties(subject, oneSubject1);
            //根据一级分类获取二级分类列表
            QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("parent_id", subject.getId());
            List<EduSubject> eduSubjects2 = baseMapper.selectList(wrapper2);
            //遍历二级分类列表
            for (EduSubject subject2 : eduSubjects2) {
                //将二级分类对象复制到TwoSubject
                TwoSubject twoSubject = new TwoSubject();
                BeanUtils.copyProperties(subject2, twoSubject);
                //将TwoSubject添加到OneSubject的children集合中
                oneSubject1.getChildren().add(twoSubject);
            }
            //将OneSubject添加到集合中
            oneSubject.add(oneSubject1);
        }
        return oneSubject;
    }

    @Override
    public boolean deleteById(String id) {
        //根据ID查询数据库中是否有二级分类
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        List<EduSubject> eduSubjectList = baseMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(eduSubjectList)) {
            int i = baseMapper.deleteById(id);
            return i == 1;
        } else {
            return false;
        }
    }

    @Override
    public Boolean saveLevelOne(EduSubject subject) {

        //1.根据title判断一级分类是否存在
        EduSubject flag = this.selectSubjectByName(subject.getTitle());
        //2.如果存在返回false
        if (flag != null) {
            return false;
        }
        //3.如果不存在，保存
        subject.setSort(0);
        int insert = baseMapper.insert(subject);
        return insert == 1;

    }

    @Override
    public Boolean saveLevelTwo(EduSubject subject) {
        //查询改分类是否存在
        EduSubject subject1 = this.selectSubjectByNameAndParentId(subject.getTitle(), subject.getParentId());
        if(subject1 != null){
            return false;
        }
        subject.setSort(0);
        int insert = baseMapper.insert(subject);
        return insert == 1;
    }
}

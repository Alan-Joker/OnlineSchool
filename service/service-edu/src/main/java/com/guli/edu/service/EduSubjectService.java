package com.guli.edu.service;

import com.guli.edu.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.vo.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Alan_
 * @since 2021-01-10
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 根据传递的Excel表格模板返回错误的信息
     * @param file
     * @return
     */
    List<String> importExcel(MultipartFile file);

    /**
     * 获取课程分类
     * @return
     */
    List<OneSubject> getTree();

    /**
     * 根据Id删除分类
     * @param id
     * @return
     */
    boolean deleteById(String id);

    /**
     * 添加课程一级分类
     * @param subject
     * @return
     */
    Boolean saveLevelOne(EduSubject subject);

    /**
     * 添加课程二级分类
     * @param subject
     * @return
     */
    Boolean saveLevelTwo(EduSubject subject);

}

package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.CourseDescription;
import com.guli.edu.entity.EduTeacher;
import com.guli.edu.entity.frontvo.CourseFrontVo;
import com.guli.edu.entity.frontvo.CourseWebVo;
import com.guli.edu.entity.vo.CoursePublishVo;
import com.guli.edu.entity.vo.CourseQuery;
import com.guli.edu.entity.vo.CourseVo;
import com.guli.edu.mapper.CourseDescriptionMapper;
import com.guli.edu.mapper.CourseMapper;
import com.guli.edu.service.CourseDescriptionService;
import com.guli.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Alan_
 * @since 2021-01-12
 */
@Service
@Transactional
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Override
    public String saveVo(CourseVo vo) {
        //1.添加课程
        baseMapper.insert(vo.getCourse());
        //2.获取课程Id
        String courseId = vo.getCourse().getId();
        //3.添加课程描述
        CourseDescription cd = vo.getCourseDescription();
        cd.setId(courseId);
        courseDescriptionService.save(cd);
        return courseId;
    }

    @Override
    public CourseVo getCourseVoById(String id) {

        //创建一个Vo对象
        CourseVo vo = new CourseVo();
        //根据课程ID获取课程对象
        Course eduCourse = baseMapper.selectById(id);
        if(eduCourse == null){
            return vo;
        }else {
            //将课程对象添加到vo对象中
            vo.setCourse(eduCourse);
        }
        //根据课程ID获取课程描述
        CourseDescription courseDescription = courseDescriptionService.getById(id);
        if(courseDescription == null){
            return vo;
        }else {
            //将课程描述添加到vo对象中
            vo.setCourseDescription(courseDescription);
        }
        return vo;
    }

    @Override
    public Boolean updateVo(CourseVo vo) {
        //先判断ID是否存在，如果不存在直接返回false
        if(StringUtils.isEmpty(vo.getCourse().getId())){
            return false;
        }
        //修改courese
        int i = baseMapper.updateById(vo.getCourse());
        if(i <= 0){
            return false;
        }
        //修改描述
        vo.getCourseDescription().setId(vo.getCourse().getId());
        boolean b = courseDescriptionService.updateById(vo.getCourseDescription());
        return b;
    }

    @Override
    public void getPageList(Page<Course> coursePage, CourseQuery courseQuery) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if(coursePage == null){
            baseMapper.selectPage(coursePage,wrapper);
        }
        String subjectId = courseQuery.getSubjectId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String teacherId = courseQuery.getTeacherId();
        String title = courseQuery.getTitle();

        if(!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(subjectParentId)){
            wrapper.eq("subject_parent_id",subjectId);
        }
        if(!StringUtils.isEmpty(teacherId)){
            wrapper.eq("teacher_id",subjectId);
        }
        if(!StringUtils.isEmpty(title)){
            wrapper.eq("title",subjectId);
        }
        baseMapper.selectPage(coursePage,wrapper);
    }

    @Override
    public Boolean deleteById(String id) {

        //删除描述
        boolean b = courseDescriptionService.removeById(id);
        if(!b){
            return false;
        }
        //删除基本信息
        int i = baseMapper.deleteById(id);
        return i == 1;
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        CoursePublishVo vo = baseMapper.getCoursePublishVoById(id);
        return vo;
    }

    //条件查询课程并分页
    @Override
    public Map<String, Object> getCourseFrontList(Page<Course> coursePage, CourseFrontVo courseFrontVo
    ) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        //判断条件是否为空，不空拼接
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            //一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }

        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            //二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }

        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            //关注度排序
            wrapper.orderByDesc("buy_count");
        }

        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            //时间排序
            wrapper.orderByDesc("gmt_create");
        }

        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            //价格排序
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(coursePage,wrapper);

        List<Course> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        long pages = coursePage.getPages();
        //是否有下一页
        boolean hasNext = coursePage.hasNext();
        //是否有上一页
        boolean hasPrevious = coursePage.hasPrevious();
        HashMap<String, Object> map = new HashMap<>();
        map.put("items",records);
        map.put("size",size);
        map.put("current",current);
        map.put("total",total);
        map.put("pages",pages);
        map.put("hashNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        return map;
    }

    //根据课程id，查询课程信息，更新浏览量
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        //获取课程信息
        return baseMapper.getBaseCourseInfo(courseId);

    }


}

package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.CourseDescription;
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


}

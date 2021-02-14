package com.guli.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.frontvo.CourseFrontVo;
import com.guli.edu.entity.vo.CoursePublishVo;
import com.guli.edu.entity.vo.CourseQuery;
import com.guli.edu.entity.vo.CourseVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Alan_
 * @since 2021-01-12
 */
public interface CourseService extends IService<Course> {

    /**
     * 保存课程信息
     * @param vo
     * @return
     */
    String saveVo(CourseVo vo);

    /**
     * 根据课程ID查询课程基本信息和描述
     * @param id
     * @return
     */
    CourseVo getCourseVoById(String id);

    /**
     * 修改课程基本信息
     * @param vo
     * @return
     */
    Boolean updateVo(CourseVo vo);

    void getPageList(Page<Course> coursePage, CourseQuery courseQuery);

    Boolean deleteById(String id);

    /**
     * 根据课程ID查询发布课程的详情
     */
    CoursePublishVo getCoursePublishVoById(String id);

    Map<String, Object> getCourseFrontList(Page<Course> coursePage, CourseFrontVo courseFrontVo);

}

package com.guli.edu.mapper;

import com.guli.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.edu.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Alan_
 * @since 2021-01-12
 */
public interface CourseMapper extends BaseMapper<Course> {


    CoursePublishVo getCoursePublishVoById(String id);
}

package com.guli.edu.service.impl;

import com.guli.edu.entity.Course;
import com.guli.edu.entity.CourseDescription;
import com.guli.edu.entity.vo.CourseVo;
import com.guli.edu.mapper.CourseDescriptionMapper;
import com.guli.edu.mapper.CourseMapper;
import com.guli.edu.service.CourseDescriptionService;
import com.guli.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
}

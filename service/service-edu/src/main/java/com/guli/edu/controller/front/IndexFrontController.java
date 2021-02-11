package com.guli.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.base.entity.Result;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.EduTeacher;
import com.guli.edu.service.CourseService;
import com.guli.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Alan_
 * @create 2021/2/2 14:02
 */
@RestController
@CrossOrigin
@RequestMapping("/cms/indexfront")
public class IndexFrontController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    //查询前8条热门课程，查询前4条名师
    @GetMapping("/index")
    public Result index(){
        //查询前8条热门课程
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<Course> courseList = courseService.list(wrapper);


        //查询前4个名师
        QueryWrapper<EduTeacher> wrapper2 = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 4");
        List<EduTeacher> teacherList= teacherService.list(wrapper2);

        return Result.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}

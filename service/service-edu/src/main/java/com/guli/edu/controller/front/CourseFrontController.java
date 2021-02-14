package com.guli.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.base.entity.Result;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.frontvo.CourseFrontVo;
import com.guli.edu.service.CourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.Map;

/**
 * @Author Alan_
 * @create 2021/2/14 12:05
 */
@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private CourseService courseService;

    //条件查询带分页查询课程
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public Result getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                     @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<Course> coursePage = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(coursePage,courseFrontVo);
        return Result.ok().data(map);
    }
}

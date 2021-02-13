package com.guli.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.base.entity.Result;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.EduSubject;
import com.guli.edu.entity.EduTeacher;
import com.guli.edu.service.CourseService;
import com.guli.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Alan_
 * @create 2021/2/14 0:17
 */
@RestController
@RequestMapping("/eduservice/teacher")
public class TeacherFrontController  {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;
    //1.分页查询讲师的方法
    @PostMapping("/getTeacherFront/{page}/{limit}")
    public Result getTeacherFrontList(@PathVariable long page,@PathVariable long limit){

        Page<EduTeacher> pageteacher = new Page<>(page,limit);
        Map<String,Object> map = teacherService.getTeacherFrontList(pageteacher);

        //返回分页中的所有数据

        return Result.ok().data(map);
    }

    //2.讲师详情
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public Result getTeacherFrontInfo(@PathVariable long teacherId){
        EduTeacher byId = teacherService.getById(teacherId);

        //根据讲师id查询课程
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<Course> list = courseService.list(wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",list);
        map.put("item",byId);
        return Result.ok().data(map);
    }
}

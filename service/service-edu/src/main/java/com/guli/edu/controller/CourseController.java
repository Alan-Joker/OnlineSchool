package com.guli.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.base.entity.Result;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.CourseDescription;
import com.guli.edu.entity.vo.CourseQuery;
import com.guli.edu.entity.vo.CourseVo;
import com.guli.edu.service.CourseDescriptionService;
import com.guli.edu.service.CourseService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Alan_
 * @since 2021-01-12
 */
@Api(description = "课程")
@CrossOrigin
@RestController
@RequestMapping("eduservice/educourse")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    /**
     * 保存基本信息
     */
    @PostMapping("addCourseInfo")
    @ApiOperation("保存课程信息")
    public Result save(@RequestBody CourseVo vo){
        String courseId = courseService.saveVo(vo);
        return Result.ok().data("id",courseId);
    }
    /**
     * 根据课程ID获取课程基本信息和描述
     */
    @GetMapping("{id}")
    @ApiOperation("根据程ID获取课程基本信息和描述")
    public Result getCourseVoById(@PathVariable String id){
        CourseVo vo = courseService.getCourseVoById(id);
        return Result.ok().data("courseInfo",vo);
    }

    /**
     * 修改课程信息
     */
    @PutMapping("/updateVo")
    @ApiOperation("修改课程信息")
    public Result updateVo(@RequestBody CourseVo vo){
       Boolean flag =  courseService.updateVo(vo);
       if(flag){
           return Result.ok();
       }else {
           return Result.error();
       }
    }

    /**
     * 根据搜索条件分页查询
     */
    @PostMapping("{page}/{limit}")
    @ApiOperation("根据搜索条件分页查询")
    public Result getPageList(@PathVariable Integer page,
                              @PathVariable Integer limit,
                              @RequestBody(required = false) CourseQuery courseQuery){
        Page<Course> coursePage = new Page<>(page,limit);
        courseService.getPageList(coursePage,courseQuery);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows",coursePage.getRecords());
        map.put("total",coursePage.getTotal());
        return Result.ok().data(map);
    }

    /**
     * 根据课程ID删除课程信息
     */
    @DeleteMapping("/{id}")
    @ApiOperation("根据课程ID删除课程信息")
    public Result deleteById(@PathVariable String id){
        Boolean flag = courseService.deleteById(id);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
}


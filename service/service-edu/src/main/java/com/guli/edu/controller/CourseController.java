package com.guli.edu.controller;


import com.guli.base.entity.Result;
import com.guli.edu.entity.CourseDescription;
import com.guli.edu.entity.vo.CourseVo;
import com.guli.edu.service.CourseDescriptionService;
import com.guli.edu.service.CourseService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseDescriptionService courseDescriptionService;
    /**
     * 保存基本信息
     */
    @PostMapping("save")
    @ApiOperation("保存课程信息")
    public Result save(@RequestBody CourseVo vo){
        String courseId = courseService.saveVo(vo);
        return Result.ok().data("id",courseId);

    }
}


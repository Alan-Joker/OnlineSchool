package com.guli.edu.controller;


import com.guli.base.entity.Result;
import com.guli.edu.entity.EduSubject;
import com.guli.edu.entity.vo.OneSubject;
import com.guli.edu.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Alan_
 * @since 2021-01-10
 */
@RestController
@CrossOrigin
@RequestMapping("eduservice/subject")
@Api(description = "课程信息")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("/import")
    @ApiOperation("上传课程Excel信息")
    public Result importSubject(MultipartFile file){
        //因为考虑到EXCEL模板中数据不准确所以返回多个错误信息，多个错误信息放在集合中
        List<String> mesList = subjectService.importExcel(file);
        if(mesList.size() == 0){
            return Result.ok();
        }else {
            return Result.error().data("messageList",mesList);
        }
    }

    @GetMapping("/getAllSubject")
    @ApiOperation("课程分类")
    public Result TreeSubject(){
        List<OneSubject> list = subjectService.getTree();

        if(list != null){
            return Result.ok().data("subjectList",list);
        }else {
            return Result.error();
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("根据ID删除分类")
    public Result deleteById(@PathVariable String id){
        boolean flag = subjectService.deleteById(id);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @PostMapping("/saveLevelOne")
    @ApiOperation("添加课程一级分类")
    public Result saveLevelOne(@RequestBody EduSubject subject){
        Boolean flag = subjectService.saveLevelOne(subject);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
    @PostMapping("/saveLevelTwo")
    @ApiOperation("添加课程二级分类")
    public Result saveLevelTwo(@RequestBody EduSubject subject){
        Boolean flag = subjectService.saveLevelTwo(subject);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
}


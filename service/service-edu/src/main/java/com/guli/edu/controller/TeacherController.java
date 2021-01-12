package com.guli.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.EduTeacher;
import com.guli.edu.entity.vo.TeacherQuery;
import com.guli.edu.service.TeacherService;
import com.guli.base.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Alan_
 * @since 2020-12-05
 */
@RestController
@RequestMapping("eduservice/teacher")
@Api(description = "讲师管理")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService eduTeacherService;

    /*@ApiOperation(value = "所有讲师列表")
    @GetMapping("/getList")
    public Result getTeacherList(){
        List<EduTeacher> list = eduTeacherService.list(null);

        return Result.ok().data("list",list);
    }*/

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据讲师ID进行逻辑删除操作")
    public Result removeById(
            @ApiParam(name = "id",value = "讲师ID",required = true)
            @PathVariable("id")String id){

        boolean flag = eduTeacherService.removeById(id);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @ApiOperation(value = "分页查询讲师列表")
    @PostMapping("/pageTeacherCondition/{page}/{limit}")
    public Result pageList(
            @ApiParam(name = "page",value = "当前页",required = true)
            @PathVariable("page")Integer current,
            @ApiParam (name = "limit",value = "查询多少条数据",required = true)
            @PathVariable("limit")Integer limit,
            @ApiParam(name = "teacherQuery", value = "查询对象")
            @RequestBody TeacherQuery teacherQuery){
        //分页条件
        Page<EduTeacher> page = new Page<>(current,limit);
        //进行分页+条件查询
        eduTeacherService.pagequery(page,teacherQuery);
        return Result.ok().data("total",page.getTotal()).data("rows",page.getRecords());

    }

    /**
     * 添加讲师接口
     */
    @PostMapping("/addTeacher")
    @ApiOperation(value = "添加讲师")
    public Result addTeacher(
            @ApiParam(name = "eduTeacher",value = "讲师实例属性",required = true)
            @RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        return save ? Result.ok() :Result.error();

    }

    /**
     * 根据id查询讲师
     */

    @GetMapping("/getTeacher/{id}")
    @ApiOperation(value = "根据id查询讲师")
    public Result searchById(
            @ApiParam(name = "id",value = "讲师id",required = true)
            @PathVariable(value = "id")String id){

        EduTeacher teacher = eduTeacherService.getById(id);
        return Result.ok().data("teacher",teacher);
    }
    /**
     * 修改讲师接口
     */
    @ApiOperation(value = "根据讲师id修改讲师信息")
    @PutMapping("/updateTeacher")
    public Result updateById(
                             @ApiParam(name = "teacherQuery",value = "讲师实体",required = true)
                             @RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);

        return flag ? Result.ok() : Result.error();
    }
}


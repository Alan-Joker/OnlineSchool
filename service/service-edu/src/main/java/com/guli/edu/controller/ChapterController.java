package com.guli.edu.controller;


import com.guli.base.entity.Result;
import com.guli.base.exceptionhandler.GuliException;
import com.guli.edu.entity.Chapter;
import com.guli.edu.entity.vo.OneChapter;
import com.guli.edu.service.ChapterService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Alan_
 * @since 2021-01-12
 */
@Api(description = "章节相关")
@CrossOrigin
@RestController
@RequestMapping("/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    /**
     * 根据课程ID获取章节和小节列表
     */
    @GetMapping("{id}")
    @ApiOperation("根据课程ID获取章节和小节列表")
    public Result getChpterAndVideoById(@PathVariable String id){

        //1.创建一个对象，最为章节Vo，里面包括二级集合
        List<OneChapter> list = chapterService.getVoById(id);
        return Result.ok().data("list",list);
    }

    /**
     * 保存章节信息
     * @param chapter
     * @return
     */
    @PostMapping("/save")
    @ApiOperation("保存章节信息")
    public Result save(@RequestBody Chapter chapter){
        boolean save = chapterService.saveChapter(chapter);
        if(save){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    /**
     * 回显
     */
    @GetMapping("get/{id}")
    @ApiOperation("修改章节信息前的回显")
    public Result getChapterById(@PathVariable String id){
        Chapter capter = chapterService.getById(id);
        return Result.ok().data("chapter",capter);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation("章节修改操作")
    public Result updateById(@RequestBody Chapter chapter){
        try {
            chapterService.updateChapter(chapter);
            return Result.ok();
        } catch (GuliException e) {
            e.printStackTrace();
            return Result.error().message(e.getMsg());
        }
    }
    @DeleteMapping("{id}")
    @ApiOperation("根据Id删除章节")
    public Result deleteById(@PathVariable String id){
        try {
            Boolean flag = chapterService.removeChapterById(id);
            return Result.ok();
        } catch (GuliException e) {
            e.printStackTrace();
            return Result.error().message(e.getMsg());
        }
    }
}


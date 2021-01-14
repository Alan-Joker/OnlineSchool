package com.guli.edu.controller;


import com.guli.base.entity.Result;
import com.guli.edu.entity.vo.OneChapter;
import com.guli.edu.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

        //2.创建二级的Vo(video)

        //3.根据课程Id查询章节列表，遍历列表，根据每一个章节的ID查询二级列表(video集合)

        return Result.ok().data("list",list);
    }
}


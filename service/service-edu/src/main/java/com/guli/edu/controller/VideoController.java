package com.guli.edu.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.guli.base.entity.Result;
import com.guli.edu.client.VodClient;
import com.guli.edu.entity.Video;
import com.guli.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Alan_
 * @since 2021-01-14
 */
@RestController
@RequestMapping("/edu/video")
@CrossOrigin
@Api("章节视频")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VodClient vodClient;
    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    public Result save(@RequestBody Video video){
        boolean save = videoService.save(video);
        if(save){
            return Result.ok();
        }else{
            return Result.error();
        }
    }


    /**
     * 根据ID查询Video对象 回显
     */
    @GetMapping("{id}")
    @ApiOperation("根据ID查询Video对象 回显")
    public Result getVideoById(@PathVariable String id){
        Video video = videoService.getById(id);
        return Result.ok().data("video",video);
    }

    /**
     * 修改
     */
    @PutMapping("update")
    @ApiOperation("修改")
    public Result update(@RequestBody Video video){
        boolean flag = videoService.updateById(video);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    /**
     * 删除
     */
    @DeleteMapping("{id}")
    @ApiOperation("删除")
    public Result deleteById(@PathVariable String id){
        Video video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            vodClient.removeAlyVideo(videoSourceId);
        }
        boolean flag = videoService.removeVideoById(id);
        if(flag){
           return Result.ok();
        }else {
            return Result.error();
        }
    }

}


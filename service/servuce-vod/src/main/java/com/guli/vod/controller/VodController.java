package com.guli.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.guli.base.entity.Result;
import com.guli.base.exceptionhandler.GuliException;

import com.guli.vod.service.VodService;
import com.guli.vod.utils.ConstantVodUtils;
import com.guli.vod.utils.InitVodClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(description="上传视频")
@RestController
@RequestMapping("/eduvod/video")
//@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @ApiOperation(value = "上传视频到阿里云")
    @PostMapping("uploadAlyVideo")
    public Result uploadAlyVideo(MultipartFile file) {
        //返回上传视频id
        String videoId = vodService.uploadVideoAly(file);
        return Result.ok().data("videoId",videoId);
    }

    //根据视频id删除阿里云视频
    @ApiOperation(value = "根据视频id删除阿里云视频")
    @DeleteMapping("removeAlyVideo/{id}")
    public Result removeAlyVideo(@PathVariable String id) {
        vodService.removeAlyVideo(id);
        return Result.ok();
    }

    //删除多个阿里云视频的方法
    //参数多个视频id  List videoIdList
    @ApiOperation(value = "删除多个阿里云视频")
    @DeleteMapping("delete-batch")
    public Result deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreAlyVideo(videoIdList);
        return Result.ok();
    }

    //根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public Result getPlayAuth(@PathVariable String id) {
        try {
            //创建初始化对象
            DefaultAcsClient client =
                    InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return Result.ok().data("playAuth",playAuth);
        }catch(Exception e) {
            throw new GuliException(20001,"获取凭证失败");
        }
    }

}




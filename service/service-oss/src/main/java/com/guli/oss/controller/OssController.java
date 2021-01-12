package com.guli.oss.controller;

import com.guli.base.entity.Result;
import com.guli.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author Alan_
 * @create 2021/1/3 22:12
 */
@CrossOrigin
@RestController
@RequestMapping("/eduoss/fileoss")
@Api(description = "文件上传")
public class OssController {
    @Autowired
    private OssService ossService;
    //上传头像的方法
    @PostMapping
    @ApiOperation("头像上传")
    public Result uploadOssFile(MultipartFile file){
        //获取上传文件 MultipartFile
        //获取上传oss的路径
        String url = ossService.uploadFileAuatar(file);
        return Result.ok().data("url",url);
    }
}

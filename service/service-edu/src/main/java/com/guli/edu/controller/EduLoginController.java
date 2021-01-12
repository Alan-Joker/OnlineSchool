package com.guli.edu.controller;

import com.guli.base.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Alan_
 * @create 2020/12/31 17:03
 */

@Api(description = "登录管理")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {

    /**
     * 登录功能
     * @return
     */
    @ApiOperation("登录")
    @PostMapping(value = "/login")
    public Result result(){

        return Result.ok().data("token","admin");
    }

    @GetMapping("/info")
    @ApiOperation(value = "获取信息")
    public Result info(){

        return Result.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}

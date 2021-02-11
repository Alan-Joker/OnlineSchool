package com.guli.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.base.entity.Result;
import com.guli.cms.entity.Banner;
import com.guli.cms.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author Alan_
 * @since 2021-02-02
 */
@RestController
@RequestMapping("/cms/banner/admin")
@CrossOrigin
@Api(description = "首页banner")
public class BannerAdminController {

    @Autowired
    private BannerService bannerService;

    //1.分页查询banner
    @GetMapping("/pageBanner/{page}/{limit}")
    @ApiOperation("分页查询banner")
    public Result pageBanner(@PathVariable("page")Long page,
                             @PathVariable("limit")Long limit){
        Page<Banner> bannerPage = new Page<>(page,limit);
        bannerService.page(bannerPage,null);
        return Result.ok().data("items",bannerPage.getRecords()).data("total",bannerPage.getTotal());
    }

    //2.添加banner
    @PostMapping("/addbanner")
    @ApiOperation("添加banner")
    public Result addBanner(@RequestBody Banner banner){
        bannerService.save(banner);
        return Result.ok();
    }

    //3.删除banner
    @DeleteMapping("/delbanner/{id}")
    @ApiOperation("删除banner")
    public Result delBanner(@PathVariable("id") String id){
        bannerService.removeById(id);
        return Result.ok();
    }

    //4.修改banner
    @PutMapping("/update")
    @ApiOperation("修改banner")
    public Result updateBanner(@RequestBody Banner banner){
        bannerService.updateById(banner);
        return Result.ok();
    }

    //5.修改前的回显操作
    @GetMapping("/search/{id}")
    @ApiOperation("修改前的回显操作")
    public Result findById(@PathVariable String id){
        Banner b = bannerService.getById(id);

        return Result.ok().data("date",b);
    }
}


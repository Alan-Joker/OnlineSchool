package com.guli.cms.controller;


import com.guli.base.entity.Result;
import com.guli.cms.entity.Banner;
import com.guli.cms.service.BannerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author Alan_
 * @since 2021-02-02
 */
@RestController
@RequestMapping("/cms/bannerfront")
@CrossOrigin
@Api(description = "网站前台banner")
public class BannerFrontController {

    @Autowired
    private BannerService bannerService;

    @GetMapping("/getAllBanner")
    public Result index(){
        List<Banner> list = bannerService.selectIndexList();

        return Result.ok().data("list",list);
    }

}


package com.guli.user.controller;


import com.guli.base.entity.Result;
import com.guli.user.entity.UcenterMember;
import com.guli.user.entity.vo.RegisterVo;
import com.guli.user.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Alan_
 * @since 2021-02-08
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    //登录
    @PostMapping("login")
    public Result loginUser(@RequestBody UcenterMember member){

        //调用service方法实现登录
        //返回token，使用jwt
        String token = memberService.login(member);
        return Result.ok().data("token",token);
    }

    //注册
    @PostMapping("register")
    public Result register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return Result.ok();
    }

}


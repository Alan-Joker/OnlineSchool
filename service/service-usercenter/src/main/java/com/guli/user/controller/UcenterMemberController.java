package com.guli.user.controller;


import com.guli.base.entity.Result;
import com.guli.base.entity.ordervo.UcenterMemberOrder;
import com.guli.base.utils.JwtInfo;
import com.guli.base.utils.JwtUtils;
import com.guli.user.entity.UcenterMember;
import com.guli.user.entity.vo.RegisterVo;
import com.guli.user.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 根据token获取登录信息
     */
    @GetMapping("getLoginInfo")
    public Result getLoginInfo(HttpServletRequest request){
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        //UcenterMember member = memberService.getById(token.getId());
        return Result.ok().data("member",token);
    }

    //根据用户id获取用户信息
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id){
        UcenterMember member = memberService.getById(id);
        //将member中的值复制到UcenterMemberOrder
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }
}


package com.guli.eduorder.controller;


import com.guli.base.entity.Result;
import com.guli.base.utils.JwtUtils;
import com.guli.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author Alan_
 * @since 2021-02-14
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    //生成订单的方法
    @PostMapping("createOrder/{courseId}")
    public Result saveOrder(@PathVariable String courseId, HttpServletRequest request){
        //创建订单，返回订单号
        String orderNo = orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request).getId());
        return Result.ok().data("orderId",orderNo);
    }
}


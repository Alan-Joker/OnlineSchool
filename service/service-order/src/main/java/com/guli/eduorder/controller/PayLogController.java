package com.guli.eduorder.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.guli.base.entity.Result;
import com.guli.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author Alan_
 * @since 2021-02-14
 */
@RestController
@RequestMapping("/eduorder/pay-log")
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    //生成微信支付二维码接口
    @GetMapping("/createNative/{orderNo}")
    public Result createNative(@PathVariable String orderNo){
        //返回信息，包含二维码地址，还有其他信息
        Map map = payLogService.createNative(orderNo);
        return Result.ok().data(map);
    }

    /**
     * 根据订单号查询订单支付状态,
     */
    @GetMapping("/queryPayStayus/{orderNo}")
    public Result queryPayStayus(@PathVariable String orderNo){
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        if(map == null){
            return Result.error().message("支付出错了");
        }
        //map不为空，通过map获取订单状态
        if(map.get("trade_state").equals("SUCCESS")){
            //添加记录到支付表，更新订单表订单状态
            payLogService.updateOrderStatus(map);
            return Result.ok().message("支付成功");
        }
        return Result.ok().message("支付中");
    }


}


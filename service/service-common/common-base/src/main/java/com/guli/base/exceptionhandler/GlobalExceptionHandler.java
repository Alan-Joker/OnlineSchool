package com.guli.base.exceptionhandler;

import com.guli.base.entity.Result;
import com.guli.base.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author Alan_
 * @create 2020/12/21 20:41
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class) //所有异常都执行此方法
    @ResponseBody //为了返回数据
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error().message("执行了全局异常处理");
    }

    @ExceptionHandler(ArithmeticException.class) //所有算数异常都执行此方法
    @ResponseBody //为了返回数据
    public Result error2(ArithmeticException e){
        e.printStackTrace();
        return Result.error().message("执行了特定异常处理");
    }

    //自定义异常
    @ExceptionHandler(GuliException.class) //所有异常都执行此方法
    @ResponseBody //为了返回数据
    public Result error3(GuliException e){
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().code(e.getCode()).message(e.getMsg());
    }
}

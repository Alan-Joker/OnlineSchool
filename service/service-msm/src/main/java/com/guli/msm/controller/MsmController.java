package com.guli.msm.controller;

import com.guli.base.entity.Result;
import com.guli.msm.service.MsmService;
import com.guli.msm.utils.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author Alan_
 * @create 2021/2/7 20:29
 */
@RestController
@CrossOrigin
@RequestMapping("/edumsm/msm")
public class MsmController {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private MsmService msmService;

    //发短信
    @GetMapping("send/{phone}")
    public Result sendMsm(@PathVariable("phone")String phone){

        //1.从redis中获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        //2.获取不到，阿里云发送
        if(!StringUtils.isEmpty(code)){
            return Result.ok();
        }
        //生成随机值验证码
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        boolean isSend = msmService.send(map,phone);
        if(isSend){
            //发送成功，把发送成功验证码放到Redis中
            //设置有效时间
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return Result.ok();
        }else {
            return Result.error().message("短信发送失败");
        }

    }

}

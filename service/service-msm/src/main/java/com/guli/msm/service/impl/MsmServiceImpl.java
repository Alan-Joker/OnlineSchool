package com.guli.msm.service.impl;

import com.guli.msm.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author Alan_
 * @create 2021/2/7 20:44
 */
@Service
public class MsmServiceImpl implements MsmService {

    @Autowired
    private JavaMailSender javaMailSender;
    /**
     * 发送短信
     * @param map
     * @param phone
     * @return
     */
    @Override
    public boolean send(Map<String, Object> map, String phone) {

        /*if(StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI4FqTqfScRaXGUsDXWyPA", "kJkzK5EXZScqMW1fMvheVvwM9pzKtB");
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关参数  固定的
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送相关的参数
        request.putQueryParameter("PhoneNumbers", phone); //手机号
        request.putQueryParameter("SignName", "MindSchool"); //申请阿里云 签名名称
        request.putQueryParameter("TemplateCode", "SMS_187570951"); //申请阿里云 模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map)); //验证码数据，转换json数据传递

        try {
            //最终发送
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }*/


            // 构造Email消息
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("tony_xb@163.com");
            message.setTo(phone);
            message.setSubject("注册成功");
            String code = (String) map.get("code");
            message.setText("注册验证码为"+code+",5分钟有效");
            javaMailSender.send(message);
            return true;
    }
}

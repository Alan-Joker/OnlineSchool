package com.guli.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.base.exceptionhandler.GuliException;
import com.guli.base.utils.JwtInfo;
import com.guli.base.utils.JwtUtils;
import com.guli.base.utils.MD5;
import com.guli.user.entity.UcenterMember;
import com.guli.user.entity.vo.RegisterVo;
import com.guli.user.mapper.UcenterMemberMapper;
import com.guli.user.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Alan_
 * @since 2021-02-08
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    /**
     * 登录方法
     * @param member
     * @return
     */
    @Override
    public String login(UcenterMember member) {

        //获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败");
        }
        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        //判断查询出的对象是否为空
        if(mobileMember == null){
            //数据库中没有此用户
            throw new GuliException(20001,"用户不存在，登录失败");
        }

        //判断密码是否正确
        //把输入的密码进行加密，在和数据库中的密码比较
        //加密方式:MD5
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new GuliException(20001,"登录失败,密码错误");
        }

        //判断用户是否被正用
        if(mobileMember.getIsDisabled()){
            throw new GuliException(20001,"登录失败");
        }
        JwtInfo jwtInfo = new JwtInfo();
        jwtInfo.setId(mobileMember.getId());
        jwtInfo.setNickname(mobileMember.getNickname());
        String token = JwtUtils.getJwtToken(jwtInfo, 300);

        return token;
    }

    /**
     * 注册
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        //验证码
        String code = registerVo.getCode();

        //手机号
        String mobile = registerVo.getMobile();

        //昵称
        String nickname = registerVo.getNickname();

        //密码
        String password = registerVo.getPassword();
        if(StringUtils.isEmpty(code) || StringUtils.isEmpty(password)
            || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname)){
            throw  new GuliException(20001,"短信发送失败");
        }
        //验证验证码
        //获取redis中的验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        System.out.println(redisCode);
        if(redisCode == null){
            throw new GuliException(20001,"验证码失效");
        }else if(!redisCode.equals(code)){
            throw new GuliException(20001,"验证码错误");
        }
        //判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0){
            throw new GuliException(20001,"手机号已存在");
        }
        //将数据添加到数据库
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        password = MD5.encrypt(password);
        ucenterMember.setPassword(password);
        ucenterMember.setNickname(nickname);
        baseMapper.insert(ucenterMember);
    }
}

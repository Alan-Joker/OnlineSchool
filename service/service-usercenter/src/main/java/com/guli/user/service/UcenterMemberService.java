package com.guli.user.service;

import com.guli.user.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.user.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Alan_
 * @since 2021-02-08
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);
}

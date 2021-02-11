package com.guli.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.cms.entity.Banner;
import com.guli.cms.mapper.BannerMapper;
import com.guli.cms.service.BannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author Alan_
 * @since 2021-02-02
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    /**
     * 查询所有banner
     * @return
     */
    @Cacheable(key = "'selectList'",value = "banner")
    @Override
    public List<Banner> selectIndexList() {

        QueryWrapper<Object> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //last放大拼接sql语句
        wrapper.last("limit 2");
        List<Banner> list = baseMapper.selectList(null);
        return list;
    }
}

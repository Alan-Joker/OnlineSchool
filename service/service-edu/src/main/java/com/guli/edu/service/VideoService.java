package com.guli.edu.service;

import com.guli.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Alan_
 * @since 2021-01-14
 */
public interface VideoService extends IService<Video> {

    /**
     * 根据Id删除章节信息
     * @param id
     * @return
     */
    boolean removeVideoById(String id);
}

package com.guli.edu.service;

import com.guli.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.vo.OneChapter;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Alan_
 * @since 2021-01-12
 */
public interface ChapterService extends IService<Chapter> {

    /**
     * 根据课程ID获取章节和小节列表
     * @param id
     * @return
     */
    List<OneChapter> getVoById(String id);
}

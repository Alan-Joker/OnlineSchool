package com.guli.edu.service;

import com.guli.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.vo.ChapterVo;
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

    /**
     * 保存章节信息
     * @param chapter
     * @return
     */
    boolean saveChapter(Chapter chapter);

    /**
     * 修改章节
     * @param chapter
     * @return
     */
    boolean updateChapter(Chapter chapter);

    /**
     * 根据章节id删除章节信息
     * @param id
     * @return
     */
    Boolean removeChapterById(String id);

    List<ChapterVo> getChapterVideoByCourseId(String courseId);
}

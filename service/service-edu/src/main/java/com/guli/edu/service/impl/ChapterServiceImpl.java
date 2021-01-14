package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.edu.entity.Chapter;
import com.guli.edu.entity.Video;
import com.guli.edu.entity.vo.OneChapter;
import com.guli.edu.entity.vo.TwoVideo;
import com.guli.edu.mapper.ChapterMapper;
import com.guli.edu.mapper.TeacherMapper;
import com.guli.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Alan_
 * @since 2021-01-12
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Override
    public List<OneChapter> getVoById(String id) {

        List<OneChapter> list = new ArrayList<>();
        //1.根据Id查询章节列表
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        wrapper.orderByAsc("sort");
        List<Chapter> chapterList = baseMapper.selectList(wrapper);
        //2.遍历章节列表
        for (Chapter chapter : chapterList) {
            //3.把每一个章节对象复制到OneChapter中
            OneChapter oneChapter = new OneChapter();
            BeanUtils.copyProperties(chapter,oneChapter);
            //4.根据每一个章节Id查询小节列表
            QueryWrapper<Video> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("chapter_id",oneChapter.getId());
            wrapper1.orderByAsc("sort");
            List<Video> videoList = videoService.list(wrapper1);
            //5.遍历每一个小节
            for (Video video : videoList) {
                //6，把每一个小姐对象复制到TwoVideo
                TwoVideo twoVideo = new TwoVideo();
                BeanUtils.copyProperties(video, twoVideo);
                //7.将每一个TwoVideo加到章节children
                oneChapter.getChildren().add(twoVideo);
            }
            //8.将每一个章节添加到总集合
            list.add(oneChapter);
        }
        return list;
    }
}

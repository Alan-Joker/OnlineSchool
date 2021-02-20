package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.base.exceptionhandler.GuliException;
import com.guli.edu.entity.Chapter;
import com.guli.edu.entity.EduSubject;
import com.guli.edu.entity.Video;
import com.guli.edu.entity.vo.ChapterVo;
import com.guli.edu.entity.vo.OneChapter;
import com.guli.edu.entity.vo.TwoVideo;
import com.guli.edu.entity.vo.VideoVo;
import com.guli.edu.mapper.ChapterMapper;
import com.guli.edu.mapper.TeacherMapper;
import com.guli.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.edu.service.VideoService;
import org.apache.ibatis.annotations.Insert;
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
        wrapper.eq("course_id", id);
        wrapper.orderByAsc("sort");
        List<Chapter> chapterList = baseMapper.selectList(wrapper);
        //2.遍历章节列表
        for (Chapter chapter : chapterList) {
            //3.把每一个章节对象复制到OneChapter中
            OneChapter oneChapter = new OneChapter();
            BeanUtils.copyProperties(chapter, oneChapter);
            //4.根据每一个章节Id查询小节列表
            QueryWrapper<Video> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("chapter_id", oneChapter.getId());
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

    @Override
    public boolean saveChapter(Chapter chapter) {
        if (chapter == null) {
            return false;
        }
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("title", chapter.getTitle());
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            return false;
        } else {
            int insert = baseMapper.insert(chapter);
            return insert == 1;
        }
    }

    @Override
    public boolean updateChapter(Chapter chapter) {
        if (chapter == null) {
            return false;
        }
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("title", chapter.getTitle());
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new GuliException(20001,"章节名称已存在");
        } else {
            int insert = baseMapper.updateById(chapter);
            return insert == 1;
        }
    }

    @Override
    public Boolean removeChapterById(String id) {
        //1.判断当前章节下面是否存在小节
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        List<Video> list = videoService.list(wrapper);
        //2.如果有不能删除，直接false
        if(list.size() != 0){
            throw new GuliException(20001,"此章节下有小节，先删除小节");
        }else {
            int i = baseMapper.deleteById(id);
            return i == 1;
        }
    }

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //1 根据课程id查询课程里面所有的章节
        QueryWrapper<Chapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<Chapter> eduChapters = baseMapper.selectList(wrapperChapter);

        //2 根据课程id查询课程里面所有的小节
        QueryWrapper<Video> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<Video> eduVideos = videoService.list(wrapperVideo);

        //创建list集合，用于最终封装数据
        List<ChapterVo> finalList = new ArrayList<>();

        //3 遍历查询章节list集合进行封装
        //遍历查询章节list集合
        for (int i = 0; i < eduChapters.size(); i++) {
            //得到每个章节
            Chapter eduChapter = eduChapters.get(i);
            //eduChapter对象值复制到ChapterVo里面
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            //把chapterVo放到最终list集合
            finalList.add(chapterVo);

            //创建集合，用于封装章节的小节
            List<VideoVo> videoList = new ArrayList<>();

            //4 遍历查询小节list集合，进行封装
            for (int m = 0; m < eduVideos.size(); m++) {
                //得到每个小节
                Video eduVideo = eduVideos.get(m);
                //判断：小节里面chapterid和章节里面id是否一样
                if(eduVideo.getChapterId().equals(eduChapter.getId())) {
                    //进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    //放到小节封装集合
                    videoList.add(videoVo);
                }
            }

            //把封装之后小节list集合，放到章节对象里面
            chapterVo.setChildren(videoList);
        }
        return finalList;

    }
}

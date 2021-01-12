package com.guli.edu.service;

import com.guli.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.vo.CourseVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Alan_
 * @since 2021-01-12
 */
public interface CourseService extends IService<Course> {

    /**
     * 保存课程信息
     * @param vo
     * @return
     */
    String saveVo(CourseVo vo);
}

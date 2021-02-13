package com.guli.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.vo.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Alan_
 * @since 2020-12-05
 */
public interface TeacherService extends IService<EduTeacher> {

    void pagequery(Page<EduTeacher> page, TeacherQuery teacherQuery);

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageteacher);

}

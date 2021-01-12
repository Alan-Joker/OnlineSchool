package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.EduTeacher;
import com.guli.edu.entity.vo.TeacherQuery;
import com.guli.edu.mapper.TeacherMapper;
import com.guli.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author Alan_
 * @since 2020-12-05
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, EduTeacher> implements TeacherService {

    @Override
    public void pagequery(Page<EduTeacher> page, TeacherQuery teacherQuery) {
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        if(teacherQuery == null){
            baseMapper.selectPage(page,queryWrapper);
            return;
        }

        String name = teacherQuery.getName();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        Integer level = teacherQuery.getLevel();

        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if(level != null){
            queryWrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
           queryWrapper.le("gmt_create",end);
        }
        baseMapper.selectPage(page,queryWrapper);
    }
}

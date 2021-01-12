package com.guli.edu;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.EduTeacher;
import com.guli.edu.mapper.TeacherMapper;
import com.guli.edu.service.TeacherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author Alan_
 * @create 2020/12/5 20:36
 */
@SpringBootTest(classes = TeacherApplication.class)
@RunWith(SpringRunner.class)
public class Tesr2 {

    @Autowired
    private TeacherMapper teacherMapper;

    @Test
    public void test(){
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //wrapper.like("name","家");
        Page<EduTeacher> page = new Page<>(1,3);
        teacherMapper.selectPage(page,null);

        System.out.println(page.getRecords());
    }
}


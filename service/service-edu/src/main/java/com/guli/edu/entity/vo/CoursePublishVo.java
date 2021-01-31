package com.guli.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Alan_
 * @create 2021/1/15 16:11
 */
@Data
public class CoursePublishVo implements Serializable {

    private String id;

    private String title;

    private String subjectParentTitle;

    private String subjectTitle;

    private String leesonNum; //课时

    private String teacherName;

    private String price;

    private String cover; // 封面
}

package com.guli.edu.entity.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @Author Alan_
 * @create 2021/1/13 18:14
 */
@Data
public class CourseQuery implements Serializable {

    private String subjectId;

    private String subjectParentId;

    private String title;

    private String teacherId;
}

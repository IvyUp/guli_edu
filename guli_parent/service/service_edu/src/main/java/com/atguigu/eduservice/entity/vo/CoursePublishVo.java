package com.atguigu.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description：课程信息发布的实体类
 * @Author：Ivy_up
 * @Create：2022-11-03 12:15
 */
@Data
public class CoursePublishVo implements Serializable {

    private static final long serialVersionUID = 12321L;

    private String id;

    private String title;

    private String cover;

    private Integer lessonNum;

    private String subjectLevelOne;

    private String subjectLevelTwo;

    private String teacherName;

    private String price;//只用于显示

}

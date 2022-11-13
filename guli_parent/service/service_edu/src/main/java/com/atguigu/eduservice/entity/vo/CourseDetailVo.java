package com.atguigu.eduservice.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description：课程详细信息
 * @Author：Ivy_up
 * @Create：2022-11-09 9:18
 */
@Data
public class CourseDetailVo {

    private String id;

    private String title;

    private BigDecimal price;

    private Integer lessonNum;

    private String cover;

    private Long buyCount;

    private Long viewCount;

    private String description;

    private String teacherId;

    private String teacherName;

    private String intro;

    private String avatar;

    private String subjectOneId;

    private String subjectOneTitle;

    private String subjectTwoId;

    private String subjectTwoTitle;

}

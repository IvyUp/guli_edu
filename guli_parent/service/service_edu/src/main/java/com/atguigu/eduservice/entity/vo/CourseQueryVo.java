package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description：课程分页查询条件类
 * @Author：Ivy_up
 * @Create：2022-11-08 20:34
 */
@Data
public class CourseQueryVo implements Serializable {

    private String title;

    private String teacherId;

    private String subjectParentId;

    private String subjectId;

    private String buyCountSort;

    private String gmtCreateSort;

    private String priceSort;

}

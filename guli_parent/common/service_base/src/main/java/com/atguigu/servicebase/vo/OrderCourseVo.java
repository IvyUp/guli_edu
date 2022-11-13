package com.atguigu.servicebase.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description：订单中的课程信息
 * @Author：Ivy_up
 * @Create：2022-11-09 14:40
 */
@Data
public class OrderCourseVo implements Serializable {

    private String courseId;

    private String courseTitle;

    private String courseCover;

    private String teacherName;

    private BigDecimal coursePrice;

}

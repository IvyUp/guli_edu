package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description：条件查询对象类
 * @Author：Ivy_up
 * @Create：2022-10-27 16:47
 */
@Data
public class EduTeacherQuery implements Serializable {

    @ApiModelProperty(value = "姓名，支持模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔：1-高级讲师，2-首席讲师")
    private Integer level;

    @ApiModelProperty(value = "开始时间", example = "2019-01-01 10:10:10")
    //使用String类型，前端传过来的数据无需进行数据转换
    private String begin;

    @ApiModelProperty(value = "结束时间", example = "2019-01-01 10:10:10")
    private String end;
}

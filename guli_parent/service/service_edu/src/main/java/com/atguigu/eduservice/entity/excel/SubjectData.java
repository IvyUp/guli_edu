package com.atguigu.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Description：Excel对应的实体类
 * @Author：Ivy_up
 * @Create：2022-10-31 23:24
 */
@Data
public class SubjectData {

    @ExcelProperty(value = "一级课程名称", index = 0)
    private String oneSubjectName;

    @ExcelProperty(value = "二级课程名称", index = 1)
    private String twoSubjectName;

}

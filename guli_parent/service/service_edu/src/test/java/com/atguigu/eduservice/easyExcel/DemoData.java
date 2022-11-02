package com.atguigu.eduservice.easyExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Description：Excel对应的实体类
 * @Author：Ivy_up
 * @Create：2022-10-31 20:24
 */
@Data
public class DemoData {

    @ExcelProperty(value = "学生编号", index = 0)
    private Integer sno;

    @ExcelProperty(value = "学生姓名", index = 1)
    private String sname;

}

package com.atguigu.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description：一级学科对应的实体类
 * @Author：Ivy_up
 * @Create：2022-11-01 16:02
 */
@Data
public class OneSubject {

    //一级学科id
    private String id;

    //一级学科名称
    private String title;

    //一级学科包含的所有二级学科
    private List<TwoSubject> children = new ArrayList<>();

}

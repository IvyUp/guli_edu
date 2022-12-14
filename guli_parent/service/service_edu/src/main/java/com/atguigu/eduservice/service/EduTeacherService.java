package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-10-27
 */
public interface EduTeacherService extends IService<EduTeacher> {
    //获取id排名后4位的讲师
    List<EduTeacher> getTeacher();

    //讲师分页数据
    HashMap<String, Object> getTeacherPage(Page<EduTeacher> teacherPage);
}

package com.atguigu.eduservice.service;


import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.course.CourseQuery;
import com.atguigu.eduservice.entity.vo.CourseDetailVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQueryVo;
import com.atguigu.servicebase.vo.OrderCourseVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-11-01
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加新课程
    String saveCourse(CourseInfoVo courseInfoVo);

    //根据课程id，查询课程信息
    CourseInfoVo getCourseVoById(String courseId);

    //更新课程信息
    Boolean updateCourseVo(CourseInfoVo courseVo);

    //根据课程id，获取课程发布信息
    CoursePublishVo getCoursePublishVoById(String courseId);

    //根据课程id，发布课程信息
    void publishCourseById(String courseId);

    //课程分页查询
    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    //获取id排名后8位的课程
    List<EduCourse> getCourse();

    //根据讲师id，获取课程信息
    List<EduCourse> getCourseByTeacherId(String teacherId);

    //查询课程条件分页
    Map<String, Object> getCoursePageByVo(Page<EduCourse> coursePage, CourseQueryVo courseQueryVo);

    //查询课程详细信息
    CourseDetailVo getCourseDetailVoById(String courseId);

    //订单中需要的课程信息
    OrderCourseVo getOrderCourseInfo(String courseId);
}

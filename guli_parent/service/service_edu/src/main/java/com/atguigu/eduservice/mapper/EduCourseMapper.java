package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseDetailVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.servicebase.vo.OrderCourseVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2022-11-01
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getCoursePublishVoById(String courseId);

    //获取课程详情
    CourseDetailVo getCourseDetailVoById(String courseId);

    //订单中的课程信息
    OrderCourseVo getOrderCourseInfo(String courseId);

}

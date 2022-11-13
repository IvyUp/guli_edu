package com.atguigu.eduservice.service.impl;


import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.constant.CourseStatus;
import com.atguigu.eduservice.entity.course.CourseQuery;
import com.atguigu.eduservice.entity.vo.CourseDetailVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQueryVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.servicebase.exception.MyException;
import com.atguigu.servicebase.vo.OrderCourseVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-11-01
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    EduCourseDescriptionService descriptionService;


    /**
     * 添加新课程
     * @param courseInfoVo 课程表单对应的实体对象
     * @return 生成的课程id
     */
    @Override
    public String saveCourse(CourseInfoVo courseInfoVo) {

        //1 向edu_course表中添加记录
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, course);
        boolean resultCour = this.save(course);
        if (!resultCour){
            throw new MyException(20001, "添加课程失败");
        }

        //2 向edu_course_description表添加记录
        EduCourseDescription description = new EduCourseDescription();
        description.setId(course.getId());
        description.setDescription(courseInfoVo.getDescription());
        boolean resultDesc = descriptionService.save(description);
        if (!resultDesc){
            throw new MyException(20001, "添加课程简介失败");
        }

        return course.getId();
    }

    /**
     * 根据课程id，查询课程信息
     * @param courseId 课程id
     * @return CourseInfoVo
     */
    @Override
    public CourseInfoVo getCourseVoById(String courseId) {
        CourseInfoVo courseVo = new CourseInfoVo();

        //1 查询课程信息，封装到CourseVo中
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.eq("id", courseId);
        EduCourse course = this.getOne(courseWrapper);
        BeanUtils.copyProperties(course, courseVo);

        //2 查询课程简介信息，封装到CourseVo中
        QueryWrapper<EduCourseDescription> descriptionWrapper = new QueryWrapper<>();
        descriptionWrapper.eq("id", courseId);
        EduCourseDescription courseDescription = descriptionService.getOne(descriptionWrapper);
        courseVo.setDescription(courseDescription.getDescription());

        //3 返回封装好的CourseVo
        return courseVo;
    }

    /**
     * 更新课程信息
     * @param courseVo 课程信息
     * @return
     */
    @Override
    public Boolean updateCourseVo(CourseInfoVo courseVo) {

        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseVo, course);
        UpdateWrapper<EduCourse> courseUpdateWrapper = new UpdateWrapper<>();
        courseUpdateWrapper.setEntity(course);
        if (!this.updateById(course)){
            return false;
        };

        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseVo.getDescription());
        UpdateWrapper<EduCourseDescription> descriptionUpdateWrapper = new UpdateWrapper<>();
        descriptionUpdateWrapper.setEntity(courseDescription);
        if (!descriptionService.updateById(courseDescription)){
            return false;
        };

        return true;
    }

    /**
     * 根据课程id，获取课程发布信息
     * @param courseId
     * @return
     */
    @Override
    public CoursePublishVo getCoursePublishVoById(String courseId) {
        CoursePublishVo coursePublishVo = baseMapper.getCoursePublishVoById(courseId);
        if (coursePublishVo == null){
            throw new MyException(20001, "获取课程发布信息失败，courseId = " + courseId);
        }
        return coursePublishVo;
    }

    /**
     * 根据课程id，发布课程信息
     * @param courseId
     */
    @Override
    public void publishCourseById(String courseId) {
        EduCourse course = new EduCourse();
        course.setId(courseId);
        course.setStatus(CourseStatus.PUBLISHED);
        int result = baseMapper.updateById(course);
        if (result <= 0){
            throw new MyException(20001, "课程发布失败");
        }
    }

    /**
     * 课程分页查询
     * @param pageParam
     * @param courseQuery
     */
    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        if (courseQuery == null){
            this.page(pageParam, queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        if (!StringUtils.isEmpty(title)){
            queryWrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(teacherId)){
            queryWrapper.eq("teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(subjectParentId)){
            queryWrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)){
            queryWrapper.eq("subject_id", subjectId);
        }

       this.page(pageParam, queryWrapper);
    }

    /**
     * 获取id排名后8位的课程
     * @return
     */
    @Override
    public List<EduCourse> getCourse() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id").last("limit 8");
        return baseMapper.selectList(wrapper);
    }

    /**
     *
     * @param teacherId 讲师id
     * @return 讲师的课程信息
     */
    @Override
    public List<EduCourse> getCourseByTeacherId(String teacherId) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> courseList = baseMapper.selectList(wrapper);
        return courseList;
    }


    @Override
    public Map<String, Object> getCoursePageByVo(Page<EduCourse> coursePage, CourseQueryVo courseQueryVo) {
        //获取查询条件
        String subjectParentId = courseQueryVo.getSubjectParentId();    //一级学科
        String subjectId = courseQueryVo.getSubjectId();    //二级学科
        String buyCountSort = courseQueryVo.getBuyCountSort();  //销量排序
        String gmtCreateSort = courseQueryVo.getGmtCreateSort();    //开课时间排序
        String priceSort = courseQueryVo.getPriceSort();    //课程价格排序

        //条件查询
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(subjectParentId)){
            wrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id", subjectId);
        }
        if (!StringUtils.isEmpty(buyCountSort)){
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(gmtCreateSort)){
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(priceSort)){
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(coursePage, wrapper);

        //封装数据
        List<EduCourse> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasPrevious = coursePage.hasPrevious();
        boolean hasNext = coursePage.hasNext();

        HashMap<String, Object> courseMap = new HashMap<>();
        courseMap.put("records",records);
        courseMap.put("current",current);
        courseMap.put("pages",pages);
        courseMap.put("size",size);
        courseMap.put("total",total);
        courseMap.put("hasPrevious",hasPrevious);
        courseMap.put("hasNext",hasNext);

        return courseMap;
    }

    /**
     *
     * @param courseId
     * @return 课程详细信息
     */
    @Override
    public CourseDetailVo getCourseDetailVoById(String courseId) {
        CourseDetailVo courseDetailVo = baseMapper.getCourseDetailVoById(courseId);
        if (courseDetailVo == null){
            throw new MyException(20001, "获取课程详细信息失败");
        }
        return courseDetailVo;
    }

    /**
     * 获取订单中的课程信息
     * @param courseId
     * @return
     */
    @Override
    public OrderCourseVo getOrderCourseInfo(String courseId) {
        OrderCourseVo orderCourseVo = baseMapper.getOrderCourseInfo(courseId);
        if (orderCourseVo == null){
            throw new MyException(20001, "获取订单中的课程信息失败");
        }
        return orderCourseVo;
    }

}

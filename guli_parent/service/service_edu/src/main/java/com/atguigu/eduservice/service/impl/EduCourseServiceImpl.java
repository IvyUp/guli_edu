package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.constant.CourseStatus;
import com.atguigu.eduservice.entity.course.CourseQuery;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.servicebase.exception.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
            baseMapper.selectPage(pageParam, queryWrapper);
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

        baseMapper.selectPage(pageParam, queryWrapper);
    }

}

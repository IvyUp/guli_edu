package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.course.CourseQuery;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.EduTeacherQuery;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exception.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-11-01
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduVideoService videoService;

    /**
     * 添加新课程
     * @param courseInfoVo 课程表单对应对象
     * @return 生成的课程id
     */
    @PostMapping("/add")
    public R addCourse(@RequestBody CourseInfoVo courseInfoVo){
        String courseId = courseService.saveCourse(courseInfoVo);
        if (!StringUtils.isEmpty(courseId)){
            return R.ok().data("courseId",courseId);
        }else {
            return R.error().message("保存失败");
        }
    }

    /**
     * 根据课程id，查询课程信息
     * @param courseId 课程id
     * @return CourseInfoVo
     */
    @GetMapping("/get/{courseId}")
    public R getCourseVoById(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseVoById(courseId);
        return R.ok().data("items", courseInfoVo);
    }

    /**
     * 更新课程信息
     * @param courseVo 课程信息
     * @return
     */
    @PostMapping("/update")
    public R updateCourse(@RequestBody CourseInfoVo courseVo){
        Boolean updated = courseService.updateCourseVo(courseVo);
        return R.ok();
    }

    /**
     * 添加课程发布信息
     * @param courseId
     * @return
     */
    @GetMapping("/publish/{courseId}")
    public R getCoursePublishVoById(@PathVariable String courseId){
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVoById(courseId);
        return R.ok().data("items",coursePublishVo);
    }

    /**
     * 修改课程发布信息
     * @param courseId
     * @return
     */
    @PutMapping("/publish/{courseId}")
    public R publishCourseById(@PathVariable String courseId){
        courseService.publishCourseById(courseId);
        return R.ok();
    }

    @PostMapping("/page/{page}/{limit}")
    public R pageQuery(@PathVariable("page") Long page,
                       @PathVariable("limit") Long limit,
                       @RequestBody(required = false) CourseQuery courseQuery){
        Page<EduCourse> pageParam = new Page<>(page, limit);
        courseService.pageQuery(pageParam,courseQuery);
        List<EduCourse> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 根据课程id，删除课程 + 章节 + 小节
     * @param courseId
     * @return
     */
    @DeleteMapping("/delete/{courseId}")
    public R removeCourseById(@PathVariable String courseId){
        //1 删除课程小节
        videoService.removeVideoByCourseId(courseId);
        //2 删除课程章节
        chapterService.removeChapterByCourseId(courseId);
        //3 删除课程
        courseService.removeById(courseId);
        return R.ok();
    }

}


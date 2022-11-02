package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

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





}


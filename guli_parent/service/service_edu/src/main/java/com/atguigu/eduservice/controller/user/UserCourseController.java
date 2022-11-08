package com.atguigu.eduservice.controller.user;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.CourseQueryVo;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-05 0:05
 */
@RestController
@CrossOrigin
@RequestMapping("/user/eduservice/course")
public class UserCourseController {

    @Autowired
    private EduCourseService courseService;

    /**
     * 获取id排名后8位的课程
     * @return
     */
    @GetMapping("/get")
    public R getCourse(){
        List<EduCourse> courseList = courseService.getCourse();
        return R.ok().data("items",courseList);
    }

    /**
     *
     * @param page 当前页
     * @param limit 每页记录数
     * @param courseQueryVo 课程条件
     * @return
     */
    @PostMapping("/page/{page}/{limit}")
    public R getCoursePageByVo(@PathVariable long page,
                               @PathVariable long limit,
                               @RequestBody(required = false) CourseQueryVo courseQueryVo){
        Page<EduCourse> coursePage = new Page<>(page, limit);
        Map<String, Object> courseMap = courseService.getCoursePageByVo(coursePage, courseQueryVo);
        return R.ok().data(courseMap);
    }

}

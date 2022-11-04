package com.atguigu.eduservice.controller.user;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}

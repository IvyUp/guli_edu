package com.atguigu.eduservice.controller.user;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-05 0:00
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/user/teacher")
public class UserTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    /**
     * 获取id排名后4位的讲师
     * @return
     */
    @GetMapping("/get")
    public R getTeacher(){
        List<EduTeacher> teacherList = teacherService.getTeacher();
        return R.ok().data("items",teacherList);
    }

    /**
     * 用户端讲师分页数据
     * @return
     */
    @GetMapping("/page/{page}/{limit}")
    public R getTeacherPage(@PathVariable("page") long page,
                            @PathVariable("limit") long limit){
        Page<EduTeacher> teacherPage = new Page<>(page,limit);
        HashMap<String, Object> teacherMap = teacherService.getTeacherPage(teacherPage);
        return R.ok().data(teacherMap);
    }

    /**
     * @param teacherId 讲师id
     * @return 讲师信息 + 课程信息
     */
    @GetMapping("/get/{teacherId}")
    public R getTeacherInfoById(@PathVariable String teacherId){
        //获取讲师信息
        EduTeacher teacher = teacherService.getById(teacherId);
        //获取课程信息
        List<EduCourse> courseList = courseService.getCourseByTeacherId(teacherId);
        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }


}

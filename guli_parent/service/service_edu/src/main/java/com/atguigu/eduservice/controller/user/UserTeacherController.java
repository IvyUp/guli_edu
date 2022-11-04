package com.atguigu.eduservice.controller.user;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
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
 * @Create：2022-11-05 0:00
 */
@RestController
@CrossOrigin
@RequestMapping("/user/eduservice/teacher")
public class UserTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    /**
     * 获取id排名后4位的讲师
     * @return
     */
    @GetMapping("/get")
    public R getTeacher(){
        List<EduTeacher> teacherList = teacherService.getTeacher();
        return R.ok().data("items",teacherList);
    }



}

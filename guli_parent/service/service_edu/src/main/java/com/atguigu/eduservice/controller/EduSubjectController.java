package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-10-31
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("/add")
    public R addSubject(MultipartFile file){
        //获取上传的excel文件
        subjectService.importSubjectData(file, subjectService);

        return R.ok();
    }

    /**
     * 获取学科列表（树形）
     * @return
     */
    @GetMapping("/list")
    public R getSubjectList(){
        List<OneSubject> subjectList = subjectService.getSubjectList();
        return R.ok().data("list", subjectList);
    }


}


package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.EduTeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exception.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-10-27
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin //支持请求跨域
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    /**
     * 查询所有教师信息
     * @return
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/get/all")
    public R getAllTeacher(){
        List<EduTeacher> teachers = teacherService.list(null);

        if (teachers == null || teachers.size() == 0){
            return R.error();
        }else {
            return R.ok().data("items", teachers);
        }

    }

    /**
     * 根据id删除讲师
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("/delete/{id}")
    public R deleteTeacherById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable("id") String id){

        boolean result = teacherService.removeById(id);

        if (result){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 分页查询所有讲师
     * @param current 目标页
     * @param recode 每页展示条数
     * @return
     */
    @ApiOperation("讲师分页列表")
    @GetMapping("/get/{current}/{recode}")
    public R getTeacherPage( @PathVariable("current") Long current,
                             @PathVariable("recode") Long recode){
        //参数1：当前页码，参数2：每页记录数
        IPage<EduTeacher> pageTeacher = new Page<>(current,recode);
        //此方法自动将查询到的数据封装回pageTeacher对象
        teacherService.page(pageTeacher, null);
        List<EduTeacher> records = pageTeacher.getRecords(); //当前页数据
        long total = pageTeacher.getTotal(); //总记录数

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("records",records);
        return R.ok().data(map);
    }

    /**
     * 根据条件查询教师信息，并分页
     * @param condition 教师条件
     * @param current 目标页码
     * @param recode 每页条数
     * @return
     */
    @ApiOperation(value = "分页讲师列表")
    @PostMapping("/condition/{current}/{recode}")
    public R getTeacherPageByCondition(@RequestBody(required = false) EduTeacherQuery condition,
                                       @PathVariable("current") Long current,
                                       @PathVariable("recode") Long recode){

        //组装查询条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //根据添加时间降序排列
        wrapper.orderByDesc("gmt_create");

        //获取查询条件
        String name = condition.getName();
        Integer level = condition.getLevel();
        String begin = condition.getBegin();
        String end = condition.getEnd();

        if (!StringUtils.isEmpty(name)){
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified", end);
        }

        //分页查询
        Page<EduTeacher> page = new Page<>(current, recode);
        teacherService.page(page, wrapper);

        //获取page中的信息
        List<EduTeacher> records = page.getRecords();
        long total = page.getTotal();

        //返回查询结果
        Map<String,Object> map = new HashMap<>();
        map.put("total", total);
        map.put("records", records);
        return R.ok().data(map);
    }

    /**
     * 添加讲师
     * @param teacher
     * @return
     */
    @ApiOperation(value = "新增讲师")
    @PostMapping("/add")
    public R addTeacher(@RequestBody EduTeacher teacher){
        boolean result = teacherService.save(teacher);
        if (result){
            return R.ok().data("teacher", teacher);
        }else {
            return R.error();
        }
    }

    /**
     * 根据ID查询讲师
     * @param id
     * @return
     */
    @ApiOperation("根据ID查询讲师")
    @GetMapping("/get/{id}")
    public R getTeacherById(@PathVariable("id") String id){
        EduTeacher teacher = teacherService.getById(id);
        if (teacher != null){
            return R.ok().data("item", teacher);
        }else {
            return R.error();
        }
    }

    @PutMapping("/update/{id}")
    public R updateTeacherById(@PathVariable("id") String id,
                               @RequestBody EduTeacher teacher){
        teacher.setId(id);
        boolean result = teacherService.updateById(teacher);
        if (result){
            return R.ok().data("items", teacher);
        }else {
            return R.error();
        }
    }


}


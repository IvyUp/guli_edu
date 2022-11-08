package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-10-27
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    /**
     * 获取id排名后4位的讲师
     * @return
     */
    @Override
    public List<EduTeacher> getTeacher() {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id").last("limit 4");
        List<EduTeacher> teacherList = baseMapper.selectList(wrapper);
        return teacherList;
    }

    /**
     * 讲师分页数据
     * @param teacherPage
     * @return
     */
    @Override
    public HashMap<String, Object> getTeacherPage(Page<EduTeacher> teacherPage) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(teacherPage, wrapper);

        //取出分页数据
        List<EduTeacher> records = teacherPage.getRecords();    //当前页数据
        long total = teacherPage.getTotal();    //总记录数
        long current = teacherPage.getCurrent();    //当前页码
        long size = teacherPage.getSize();  //当前页记录数
        long pages = teacherPage.getPages();
        boolean hasPrevious = teacherPage.hasPrevious();    //是否有上一页
        boolean hasNext = teacherPage.hasNext();    //是否有下一页

        HashMap<String, Object> map = new HashMap<>();
        map.put("items",records);
        map.put("total",total);
        map.put("current",current);
        map.put("size",size);
        map.put("pages",pages);
        map.put("hasPrevious",hasPrevious);
        map.put("hasNext",hasNext);

        return map;
    }


}

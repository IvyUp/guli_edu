package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exception.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Map;

/**
 * @Description：Excel监听器
 * @Author：Ivy_up
 * @Create：2022-10-31 23:27
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    private EduSubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null){
            throw new MyException(20001, "添加失败");
        }

        //添加一级分类
        EduSubject OneSubject = this.selectOneSubject(subjectService, subjectData.getOneSubjectName());
        if (OneSubject == null){   //一级学科不存在
            OneSubject = new EduSubject();
            OneSubject.setTitle(subjectData.getOneSubjectName());
            OneSubject.setParentId("0");
            subjectService.save(OneSubject);
        }

        //获取一级分类的id值
        String oneSubjectId = OneSubject.getId();

        //添加二级分类
        EduSubject twoSubject = this.selectTwoSubject(subjectService, oneSubjectId, subjectData.getTwoSubjectName());

        if (twoSubject == null){
            twoSubject = new EduSubject();
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            twoSubject.setParentId(oneSubjectId);
            subjectService.save(twoSubject);
        }

    }

    //读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    //查询一级学科
    private EduSubject selectOneSubject(EduSubjectService subjectService, String oneSubjectName){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", oneSubjectName);
        wrapper.eq("parent_id", 0);
        EduSubject OneSubject = subjectService.getOne(wrapper);
        return OneSubject;
    }

    //查询二级学科
    private EduSubject selectTwoSubject(EduSubjectService subjectService, String oneSubjectId, String twoSubjectName){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", twoSubjectName);
        wrapper.eq("parent_id", oneSubjectId);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }

}

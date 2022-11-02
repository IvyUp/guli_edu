package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exception.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-10-31
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void importSubjectData(MultipartFile file, EduSubjectService subjectService) {
        try {
            //获取文件输入流
            InputStream inputStream = file.getInputStream();
            //读取Excel字段
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException(20002, "添加课程分类失败");
        }
    }

    /**
     * 获取学科列表（树形）
     * @return
     */
    @Override
    public List<OneSubject> getSubjectList() {
        //1 获取一级学科列表
        QueryWrapper<EduSubject> oneSubjectWrapper = new QueryWrapper<>();
        oneSubjectWrapper.eq("parent_id", "0");
        List<EduSubject> srcOneSubjectList = baseMapper.selectList(oneSubjectWrapper);

        //2 获取二级学科列表
        QueryWrapper<EduSubject> twoSubjectWrapper = new QueryWrapper<>();
        twoSubjectWrapper.ne("parent_id", "0");
        List<EduSubject> srcTwoSubjectList = baseMapper.selectList(twoSubjectWrapper);

        //3 将需要的数据封装到OneSubject中
        List<OneSubject> distOneSubjectList = new ArrayList<>();
        for (int i = 0; i < srcOneSubjectList.size(); i++) {
            EduSubject srcOneSubject = srcOneSubjectList.get(i);
            OneSubject distOneSubject = new OneSubject();
            //从eduSubject对象中，复制id和title属性
            BeanUtils.copyProperties(srcOneSubject, distOneSubject);

            //3.1 封装一级学科下面的二级学科
            List<TwoSubject> distTwoSubjectList = new ArrayList<>();
            for (int j = 0; j < srcTwoSubjectList.size(); j++) {
                EduSubject srcTwoSubject = srcTwoSubjectList.get(j);
                //判断二级学科是否属于当前一级学科
                if (srcTwoSubject.getParentId().equals(distOneSubject.getId())){
                    TwoSubject distTwoSubject = new TwoSubject();
                    BeanUtils.copyProperties(srcTwoSubject, distTwoSubject);
                    distTwoSubjectList.add(distTwoSubject);
                }
            }

            //3.2 将封装完的二级学科list，添加到一级学科的children属性
            distOneSubject.setChildren(distTwoSubjectList);

            //3.3 将一个完整的一级学科对象，添加到list中
            distOneSubjectList.add(distOneSubject);
        }

        return distOneSubjectList;
    }

}

package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-11-01
 */
public interface EduChapterService extends IService<EduChapter> {

    //获取章节小节数据
    List<ChapterVo> getChapterList(String courseId);

    //根据课程id，删除所有章节
    Boolean removeChapterByCourseId(String courseId);
}


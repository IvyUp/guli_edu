package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exception.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-11-01
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    /**
     * 获取章节和小节数据
     * @param courseId 课程id
     * @return 包含小节对象的章节集合
     */
    @Override
    public List<ChapterVo> getChapterList(String courseId) {

        //1 根据课程id获取章节数据
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", courseId);
        List<EduChapter> chapterList = this.list(chapterWrapper);

        //2 根据课程id获取小节数据
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", courseId);
        List<EduVideo> videoList = videoService.list(videoWrapper);


        //3.1 封装章节数据到ChapterVo
        List<ChapterVo> chapterVoList = new ArrayList<>();
        for (int i = 0; i < chapterList.size(); i++) {
            EduChapter chapter = chapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);

            //3.2 封装小节数据到VideoVo
            List<VideoVo> videoVoList = new ArrayList<>();
            for (int j = 0; j < videoList.size(); j++) {
                EduVideo video = videoList.get(j);
                if (video.getChapterId().equals(chapterVo.getId())){  //String 不能用 ==
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);

            chapterVoList.add(chapterVo);
        }

        //4 返回章节集合
        return chapterVoList;
    }

    /**
     * 根据章节id，删除章节
     * @param chapterId
     * @return
     */
    @Override
    public boolean deleteChapterById(String chapterId) {
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id", chapterId);
        int count = videoService.count(videoQueryWrapper);
        if (count > 0){
            throw new MyException(20001,"此章节存在小节，禁止删除，chapterId = " + chapterId);
        }
        boolean result = this.removeById(chapterId);
        return result;
    }
}

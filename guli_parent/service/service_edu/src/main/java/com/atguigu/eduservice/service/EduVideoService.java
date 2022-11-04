package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.video.VideoInfoForm;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-11-01
 */
public interface EduVideoService extends IService<EduVideo> {

    //更新小节数据
    void updateVideoInfoById(VideoInfoForm videoInfoForm);

    //根据课程id，删除所有小节 + 阿里云视频
    Boolean removeVideoByCourseId(String courseId);

    //根据章节id，删除所有小节 + 阿里云视频
    void removeVideoByChapterId(String chapterId);

    //根据小节id，删除小节 + 阿里云视频
    void removeVideoById(String videoId);
}



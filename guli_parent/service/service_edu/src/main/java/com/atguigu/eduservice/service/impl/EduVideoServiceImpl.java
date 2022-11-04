package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.video.VideoInfoForm;
import com.atguigu.eduservice.feign.VodFeign;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exception.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-11-01
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodFeign vodFeign;

    @Override
    public void updateVideoInfoById(VideoInfoForm videoInfoForm) {
        EduVideo video = new EduVideo();
        BeanUtils.copyProperties(videoInfoForm, video);
        video.setIsFree(videoInfoForm.getFree());
        boolean result = this.updateById(video);
        if (!result){
            throw new MyException(20001,"小节信息更新失败");
        }
    }

    /**
     * 根据课程id，删除下属的所有小节 + 阿里云存放的视频
     * @param courseId 课程id
     * @return
     */
    @Override
    public Boolean removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.select("video_source_id");
        List<EduVideo> videoList = baseMapper.selectList(wrapper);
        for (EduVideo video : videoList) {
            String videoSourceId = video.getVideoSourceId();
            //判断是否有视频
            if (!StringUtils.isEmpty(videoSourceId)){
                //删除视频
                vodFeign.deleteVideoById(videoSourceId);
            }
        }
        // 删除小节
        int result = baseMapper.delete(wrapper);
        return result > 0;
    }

    /**
     * 根据章节id, 删除小节 + 视频
     * @param chapterId
     */
    @Override
    public void removeVideoByChapterId(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        //1 删除视频
        wrapper.select("video_source_id");
        List<EduVideo> videoList = baseMapper.selectList(wrapper);
        if (videoList.isEmpty()){
            return;
        }
        List<String> videoSourceIds = new ArrayList<>();
        for (EduVideo video : videoList) {
            String videoSourceId = video.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)){
                videoSourceIds.add(videoSourceId);
            }
        }
        if (videoSourceIds.isEmpty()){
            return;
        }
        vodFeign.deleteVideoBatchByIds(videoSourceIds);

        //2 删除小节
        baseMapper.delete(wrapper);
    }

}

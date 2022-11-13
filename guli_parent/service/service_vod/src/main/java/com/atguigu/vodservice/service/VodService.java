package com.atguigu.vodservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-03 20:13
 */
public interface VodService {
    //上传视频
    String uploadVod(MultipartFile file);

    //删除视频
    void deleteVideoById(String videoId);

    //批量删除视频
    void deleteVideoBatchByIds(List<String> videoIds);

    //获取视频播放凭证
    String getVideoPlayAuth(String videoId);
}

package com.atguigu.vodservice.service;

import org.springframework.web.multipart.MultipartFile;

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
}

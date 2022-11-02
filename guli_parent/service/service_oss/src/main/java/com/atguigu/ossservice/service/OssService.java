package com.atguigu.ossservice.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-10-31 15:49
 */
public interface OssService {

    //上传文件到阿里云
    String uploadFile(MultipartFile file);

}

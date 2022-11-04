package com.atguigu.ossservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.ossservice.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-10-31 15:49
 */
@RestController
@RequestMapping("/eduoss/file")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    /**
     * 上传用户头像到oss
     * @return
     */
    @PostMapping("/upload/avatar")
    public R uploadAvatar(MultipartFile file){
        String avatarUrl = ossService.uploadFile(file);
        return R.ok().data("url", avatarUrl);
    }




}

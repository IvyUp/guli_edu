package com.atguigu.vodservice.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.atguigu.servicebase.exception.MyException;
import com.atguigu.vodservice.service.VodService;
import com.atguigu.vodservice.util.AliyunVodSDKUtil;
import com.atguigu.vodservice.util.ConstantPropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.atguigu.vodservice.util.AliyunVodSDKUtil.initVodClient;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-03 20:14
 */
@Service
public class VodServiceImpl implements VodService {

    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    @Override
    public String uploadVod(MultipartFile file) {
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String fileName = file.getOriginalFilename();;
        String title = fileName.substring(0, fileName.lastIndexOf("."));
        InputStream inputStream = null;
        String videoId = null;
        try {
            inputStream = file.getInputStream();
            videoId = uploadStream(accessKeyId, accessKeySecret, title, fileName, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (videoId != null){
            return videoId;
        }else {
            throw new MyException(20001, "上传视频失败");
        }
    }

    private static String uploadStream(String accessKeyId, String accessKeySecret, String title, String fileName, InputStream inputStream) {
        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        if (response.isSuccess()) {
            return response.getVideoId();
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            return response.getVideoId();
        }
    }


    /**
     * 根据视频id，删除视频
     * @param videoId
     */
    @Override
    public void deleteVideoById(String videoId) {
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        try {
            DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量删除视频
     * @param videoIds
     */
    @Override
    public void deleteVideoBatchByIds(List<String> videoIds) {
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        try {
            DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
            DeleteVideoRequest request = new DeleteVideoRequest();
            String ids = StringUtils.join(videoIds.toArray(), ",");
            request.setVideoIds(ids);
            client.getAcsResponse(request);
        }catch (ClientException e) {
            e.printStackTrace();
        }
    }

}





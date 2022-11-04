package com.atguigu.aliyunvod.test;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.junit.Test;

import java.util.List;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-03 16:48
 */
public class AliyunVodSDKTest {

    private String accessKeyId = "LTAI5tCfubNwavXtuGGM3Gp1";
    private String accessKeySecret = "nRrvawaBjuiiQk3gTkOQjDhJsAkSij";

    private String title = "6 - What If I Want to Move Faster / upload";
    private String fileName ="E:/temp/6 - What If I Want to Move Faster.mp4";

    @Test
    public void testUploadVideo(){
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }


    /**
     * 获取视频播放凭证
     * 注意：可以播放加密视频
     */
    @Test
    public void testGetPlayAuth() throws ClientException {
        //1 初始化客户端、请求对象、响应对象
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);
        GetVideoPlayAuthRequest authRequest = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse authResponse = new GetVideoPlayAuthResponse();

        //2 设置请求参数
        authRequest.setVideoId("a34e1708407141d4b54d23ae466694fa");

        //3 获取响应数据
        authResponse = client.getAcsResponse(authRequest);

        //4 输出结果
        System.out.println("播放凭证：PlayAuth = " + authResponse.getPlayAuth());
        System.out.println("VideoMeta信息：VideoMeta.Title = " + authResponse.getVideoMeta().getTitle());
    }



    /**
     * 获取视频播放地址
     * 注意：此种方式只能获取非加密视频的播放
     */
    @Test
    public void testGetPlayInfo() throws ClientException {
        //1 初始化客户端、请求对象、响应对象
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //2 设置请求参数
        request.setVideoId("a34e1708407141d4b54d23ae466694fa");

        //3 获取响应
        response = client.getAcsResponse(request);

        //4 输出请求结果
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.println("视频播放地址：" + playInfo.getPlayURL());
        }

    }

}

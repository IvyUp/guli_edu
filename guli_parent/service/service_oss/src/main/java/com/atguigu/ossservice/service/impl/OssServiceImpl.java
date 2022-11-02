package com.atguigu.ossservice.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.ossservice.service.OssService;
import com.atguigu.ossservice.util.ConstantUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;


/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-10-31 15:49
 */
@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFile(MultipartFile file) {

        //获取阿里云存储相关常量
        String endpoint = ConstantUtil.ENDPOINT;
        String accessKeyId = ConstantUtil.KEY_ID;
        String accessKeySecret = ConstantUtil.KEY_SECRET;
        String bucketName = ConstantUtil.BUCKET_NAME;

        try {
            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();

            //获取文件名称
            String fileName = file.getOriginalFilename();
            //文件名添加随机值，防止文件覆盖
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //把文件按照日期进行分类
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath + "/" + uuid + fileName;

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //调用oss方法实现上传
            ossClient.putObject(bucketName, fileName, inputStream);
            //关闭OSSClient
            ossClient.shutdown();

            //把上传之后的文件路径返回
            String url = "https://" + bucketName + endpoint + "/" + fileName;

            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}

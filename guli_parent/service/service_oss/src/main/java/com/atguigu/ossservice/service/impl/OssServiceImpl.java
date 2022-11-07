package com.atguigu.ossservice.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.atguigu.ossservice.service.OssService;
import com.atguigu.ossservice.util.ConstantUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-10-31 15:49
 */
@Service
public class OssServiceImpl implements OssService {

//    @Override
//    public String uploadFile(MultipartFile file) {
//
//        //获取阿里云存储相关常量
//        String endpoint = ConstantUtil.ENDPOINT;
//        String accessKeyId = ConstantUtil.KEY_ID;
//        String accessKeySecret = ConstantUtil.KEY_SECRET;
//        String bucketName = ConstantUtil.BUCKET_NAME;
//
//        try {
//            //获取上传文件输入流
//            InputStream inputStream = file.getInputStream();
//
//            //获取文件名称
//            String fileName = file.getOriginalFilename();
//            //文件名添加随机值，防止文件覆盖
//            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//            //把文件按照日期进行分类
//            String datePath = new DateTime().toString("yyyy/MM/dd");
//            fileName = datePath + "/" + uuid + fileName;
//
//            //设置HTTP头里面的Content-Type
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
//
//            // 创建OSSClient实例。
//            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//            //调用oss方法实现上传
//            ossClient.putObject(bucketName, fileName, inputStream);
//            //关闭OSSClient
//            ossClient.shutdown();
//
//            //把上传之后的文件路径返回
//            String url = "https://" + bucketName + endpoint + "/" + fileName;
//
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }

    @Value("${server.port}")
    private String serverPort;

//    @Autowired
//    private ServletContext servletContext;

    @Override
    public String uploadFile(MultipartFile file) {
        if (!file.isEmpty()){
            //1 文件的存放位置
            //虚拟路径：浏览器通过Tomcat服务器访问Web应用中的资源时使用的路径
            //String destFileFolderVirtualPath = "/static";
            //真实物理路径：在硬盘空间里能够直接找到文件的路径。
           // String destFileFolderRealPath = servletContext.getRealPath(destFileFolderVirtualPath);

            //上传文件路径
            String destFileFolderRealPath = "E:\\guliedu\\guli_parent\\service\\service_oss\\src\\main\\resources\\static";

            //2 保存文件的文件名
            String generatedFileName = UUID.randomUUID().toString().replace("-", "");
            //获取源文件扩展名
            String fileExtName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            //保存文件的文件名
            String destFileName = generatedFileName + "" + fileExtName;

            //3 保存文件的路径
            String destFilePath = destFileFolderRealPath + "/" + destFileName;

            //4 创建File对象，对应文件具体保存的位置
            File destFile = new File(destFilePath);

            //5 执行转存
            try {
                file.transferTo(destFile);
                return "http://localhost:" + serverPort + "/static/" + destFileName;
            } catch (IOException e) {
                e.printStackTrace();
            }



            //上传文件名
//            String fileName = UUID.randomUUID().toString().replace("-","" ) + file.getOriginalFilename();
//
//            Path filePath = Paths.get(path + fileName);
//
//            try {
//                Files.write(filePath, file.getBytes());
//                return "http://localhost:8002/" + fileName;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        return null;
    }

    /**
     * 解决问题，直接访问上传的图片地址，会让下载而不是直接访问
     * 设置设置 HTTP 头 里边的 Content-Type
     * txt 格式经过测试，不需要转换 上传之后就是 text/plain。其他未测试
     * 已知  如果 Content-Type = .jpeg 访问地址会直接下载，本方法也是解决此问题
     * @param FilenameExtension
     * @return
     */
//    public static String getcontentType(String FilenameExtension) {
//        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
//            return "static/image/bmp";
//        }
//        if (FilenameExtension.equalsIgnoreCase(".gif")) {
//            return "static/image/gif";
//        }
//        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
//                FilenameExtension.equalsIgnoreCase(".jpg") ||
//                FilenameExtension.equalsIgnoreCase(".png")) {
//            return "static/image/jpg";
//        }
//        if (FilenameExtension.equalsIgnoreCase(".html")) {
//            return "text/html";
//        }
//
//        if (FilenameExtension.equalsIgnoreCase(".txt")) {
//            return "text/plain";
//        }
//        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
//            return "application/vnd.visio";
//        }
//        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
//                FilenameExtension.equalsIgnoreCase(".ppt")) {
//            return "application/vnd.ms-powerpoint";
//        }
//        if (FilenameExtension.equalsIgnoreCase(".docx") ||
//                FilenameExtension.equalsIgnoreCase(".doc")) {
//            return "application/msword";
//        }
//        if (FilenameExtension.equalsIgnoreCase(".xml")) {
//            return "text/xml";
//        }
//        return "static/image/jpg";
//    }


}

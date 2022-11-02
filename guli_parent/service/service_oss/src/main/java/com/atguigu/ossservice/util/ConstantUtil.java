package com.atguigu.ossservice.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description：常量类，读取配置文件application.properties中的配置信息
 * @Author：Ivy_up
 * @Create：2022-10-31 15:50
 */
@Component
public class ConstantUtil implements InitializingBean {

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    public static  String ENDPOINT;

    public static String KEY_ID;

    public static String KEY_SECRET;

    public static String BUCKET_NAME;


    /**
     * 初始化配置信息
     * 此方法将在所有的属性被初始化后调用
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        ENDPOINT = endpoint;
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}

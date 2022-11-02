package com.atguigu.ossservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description：阿里云oss服务启动类
 * @Author：Ivy_up
 * @Create：2022-10-31 15:34
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.atguigu"})
public class ServiceOssMain {

    public static void main(String[] args) {
        SpringApplication.run(ServiceOssMain.class, args);
    }

}

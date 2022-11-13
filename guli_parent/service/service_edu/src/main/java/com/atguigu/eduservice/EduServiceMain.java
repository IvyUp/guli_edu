package com.atguigu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-10-27 14:32
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"})
@EnableDiscoveryClient  //注册到Nacos服务中心
@EnableFeignClients
public class EduServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(EduServiceMain.class, args);
    }

}

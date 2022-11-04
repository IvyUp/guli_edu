package com.atguigu.cmsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-04 23:37
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.atguigu")
public class CmsServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(CmsServiceMain.class, args);
    }

}

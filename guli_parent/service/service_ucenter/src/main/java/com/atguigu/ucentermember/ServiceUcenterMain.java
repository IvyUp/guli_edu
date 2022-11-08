package com.atguigu.ucentermember;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-07 14:52
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceUcenterMain {

    public static void main(String[] args) {
        SpringApplication.run(ServiceUcenterMain.class, args);
    }

}

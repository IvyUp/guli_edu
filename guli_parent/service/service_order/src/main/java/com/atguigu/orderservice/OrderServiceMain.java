package com.atguigu.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-09 14:30
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OrderServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceMain.class, args);
    }

}

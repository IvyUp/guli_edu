package com.atguigu.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-11 11:59
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling //开启定时任务
public class StatisticsServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(StatisticsServiceMain.class, args);
    }

}

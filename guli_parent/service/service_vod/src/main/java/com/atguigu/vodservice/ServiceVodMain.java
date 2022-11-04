package com.atguigu.vodservice;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-03 20:09
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.atguigu"})
public class ServiceVodMain {

    public static void main(String[] args) {
        SpringApplication.run(ServiceVodMain.class, args);
    }

}

package com.atguigu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-10-27 14:32
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"})
public class EduTeacherMain {

    public static void main(String[] args) {
        SpringApplication.run(EduTeacherMain.class, args);
    }

}

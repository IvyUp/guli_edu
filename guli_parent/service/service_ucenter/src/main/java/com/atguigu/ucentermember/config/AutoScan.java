package com.atguigu.ucentermember.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-07 14:54
 */
@Configuration
@MapperScan("com.atguigu.ucentermember.mapper")
@ComponentScan("com.atguigu")
public class AutoScan {

}

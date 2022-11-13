package com.atguigu.orderservice.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description：自动扫描类
 * @Author：Ivy_up
 * @Create：2022-11-09 14:31
 */
@Configuration
@ComponentScan("com.atguigu")
@MapperScan("com.atguigu.orderservice.mapper")
public class AutoScanConfig {
}

package com.atguigu.statistics.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * @Description：自动扫描配置类
 * @Author：Ivy_up
 * @Create：2022-11-11 12:01
 */
@Configuration
@ComponentScan("com.atguigu")   //扫描公共类
@MapperScan("com.atguigu.statistics.mapper")    //扫描自定义Mapper
public class AutoScan {
}

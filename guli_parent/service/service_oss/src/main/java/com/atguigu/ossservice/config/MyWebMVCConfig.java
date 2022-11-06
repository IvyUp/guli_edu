package com.atguigu.ossservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-06 19:48
 */
@Configuration
public class MyWebMVCConfig implements WebMvcConfigurer {

    @Value("${file.location}")
    private String fileLocation;

    @Value("${file.path}")
    private String filePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //匹配到resourceHandler,将URL映射至location,也就是本地文件夹
        registry.addResourceHandler(filePath)
                .addResourceLocations("file:///" + fileLocation);
    }

}

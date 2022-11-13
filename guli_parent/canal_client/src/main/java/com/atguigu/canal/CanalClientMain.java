package com.atguigu.canal;

import com.atguigu.canal.client.CanalClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-11 17:15
 */
@SpringBootApplication
public class CanalClientMain implements CommandLineRunner {

    @Resource
    private CanalClient canalClient;

    public static void main(String[] args) {
        SpringApplication.run(CanalClientMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //项目启动，执行canal客户端监听
        canalClient.run();
    }

}

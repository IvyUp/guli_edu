package com.atguigu.statistics.client.fallback;

import com.atguigu.statistics.client.MemberClient;
import org.springframework.stereotype.Component;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-11 12:16
 */
@Component
public class MemberClientFallback implements MemberClient {

    @Override
    public Integer getDailyRegisterCount(String date) {
        System.out.println("memberClient()执行失败，熔断器启动！！！");
        return null;
    }

}

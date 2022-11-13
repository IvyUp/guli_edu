package com.atguigu.statistics.client;

import com.atguigu.statistics.client.fallback.MemberClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-11 12:14
 */
@Component
@FeignClient(value = "service-ucenter", fallback = MemberClientFallback.class)
public interface MemberClient {

    @GetMapping("/educenter/member/statistics/{date}")
    public Integer getDailyRegisterCount(@PathVariable("date") String date);

}

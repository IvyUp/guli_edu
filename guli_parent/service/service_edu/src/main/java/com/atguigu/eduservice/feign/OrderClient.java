package com.atguigu.eduservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-11 9:37
 */
@Component
@FeignClient("service-order")
public interface OrderClient {

    @GetMapping("/orderservice/order/status/{courseId}/{memberId}")
    public Boolean isBuyCourse(@PathVariable("courseId") String courseId,
                               @PathVariable("memberId") String memberId);

}

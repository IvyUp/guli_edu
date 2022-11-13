package com.atguigu.orderservice.client;

import com.atguigu.servicebase.vo.OrderCourseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-09 15:09
 */
@FeignClient("service-edu")
@Component
public interface CourseClient {

    @GetMapping("/user/eduservice/course/order/{courseId}")
    public OrderCourseVo getOrderCourseInfo(@PathVariable("courseId") String courseId);

}

package com.atguigu.eduservice.feign;

import org.springframework.stereotype.Component;

/**
 * @Description：OrderClient熔断器
 * @Author：Ivy_up
 * @Create：2022-11-11 10:06
 */
@Component
public class OrderClientFallback implements OrderClient{

    @Override
    public Boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }

}

package com.atguigu.orderservice.client;

import com.atguigu.servicebase.vo.OrderMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-09 15:11
 */
@Component
@FeignClient("service-ucenter")
public interface MemberClient {

    @GetMapping("/educenter/member/order/{memberId}")
    public OrderMemberVo getOrderMemberInfo(@PathVariable("memberId") String memberId);

}

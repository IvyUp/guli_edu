package com.atguigu.orderservice.service;

import com.atguigu.orderservice.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-11-09
 */
public interface OrderService extends IService<Order> {

    //订单
    String createOrder(String courseId, String memberId);

    //根据课程id + 会员id，查询课程购买情况
    Boolean isBuyCourse(String courseId, String memberId);
}

package com.atguigu.orderservice.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.orderservice.entity.Order;
import com.atguigu.orderservice.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-11-09
 */
//@CrossOrigin
@RestController
@RequestMapping("/orderservice/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 根据课程id，生成新的订单
     * @param courseId 课程 id
     * @param request
     * @return 生成的订单号
     */
    @PostMapping("/create/{courseId}")
    public R createOrder(@PathVariable("courseId") String courseId,
                         HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo = orderService.createOrder(courseId, memberId);
        return R.ok().data("orderNo",orderNo);
    }

    /**
     * 获取订单信息
     * @param orderNo 订单号
     * @return
     */
    @GetMapping("/select/{orderNo}")
    public R getPayLogInfo(@PathVariable("orderNo") String orderNo){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("order",order);
    }

    @GetMapping("/status/{courseId}/{memberId}")
    public Boolean isBuyCourse(@PathVariable("courseId") String courseId,
                               @PathVariable("memberId") String memberId){
        return orderService.isBuyCourse(courseId,memberId);
    }

}


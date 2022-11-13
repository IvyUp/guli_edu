package com.atguigu.orderservice.service.impl;

import com.atguigu.orderservice.client.CourseClient;
import com.atguigu.orderservice.client.MemberClient;
import com.atguigu.orderservice.entity.Order;
import com.atguigu.orderservice.mapper.OrderMapper;
import com.atguigu.orderservice.service.OrderService;
import com.atguigu.orderservice.util.OrderNoUtil;
import com.atguigu.servicebase.exception.MyException;
import com.atguigu.servicebase.vo.OrderCourseVo;
import com.atguigu.servicebase.vo.OrderMemberVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-11-09
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private MemberClient memberClient;

    @Override
    public String createOrder(String courseId, String memberId) {
        //获取课程信息
        OrderCourseVo courseInfo = courseClient.getOrderCourseInfo(courseId);

        //获取会员信息
        OrderMemberVo memberInfo = memberClient.getOrderMemberInfo(memberId);

        //生成订单
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo()); //订单号
        order.setCourseId(courseInfo.getCourseId());    //课程id
        order.setCourseTitle(courseInfo.getCourseTitle());
        order.setCourseCover(courseInfo.getCourseCover());
        order.setTeacherName(courseInfo.getTeacherName());
        order.setMemberId(memberInfo.getMemberId());
        order.setNickname(memberInfo.getNickname());
        order.setMobile(memberInfo.getMobile());
        order.setTotalFee(courseInfo.getCoursePrice());
        order.setStatus(0);
        order.setPayType(1);

        baseMapper.insert(order);

        return order.getOrderNo();
    }

    /**
     * 根据课程id + 会员id，查询课程是否已经购买
     * @param courseId
     * @param memberId
     * @return
     */
    @Override
    public Boolean isBuyCourse(String courseId, String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1); //1表示已支付
        Integer count = baseMapper.selectCount(wrapper);

        if (count == 0){
            return false;
        }else if (count < 0 || count > 1){
            throw new MyException(20001, "查询结果有误");
        }else {
            return true;
        }

    }
}

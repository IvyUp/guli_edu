package com.atguigu.orderservice.service;

import com.atguigu.orderservice.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-11-09
 */
public interface PayLogService extends IService<PayLog> {

    //生成微信支付二维码
    Map createPaymentQR(String orderNo);

    //获取微信支付结果
    Map<String, String> getPaymentStatus(String orderNo);

    //创建支付记录
    void createPaymentLog(Map<String, String> statusMap);
}

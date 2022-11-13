package com.atguigu.orderservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.orderservice.entity.Order;
import com.atguigu.orderservice.entity.PayLog;
import com.atguigu.orderservice.mapper.PayLogMapper;
import com.atguigu.orderservice.service.OrderService;
import com.atguigu.orderservice.service.PayLogService;
import com.atguigu.orderservice.util.HttpClient;
import com.atguigu.servicebase.exception.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-11-09
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;

    @Override
    public Map createPaymentQR(String orderNo) {
        //根据订单id，获取订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);

        //设置微信支付参数
        Map<String, String> m = new HashMap();
        m.put("appid", "wx74862e0dfcf69954");
        m.put("mch_id", "1558950191");
        m.put("nonce_str", WXPayUtil.generateNonceStr());
        m.put("body", order.getCourseTitle());
        m.put("out_trade_no", orderNo);
        m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
        m.put("spbill_create_ip", "127.0.0.1");
        m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
        m.put("trade_type", "NATIVE");

        try {
            //HTTPClient来根据URL访问第三方接口，并传递参数
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //返回第三方数据
            String xmlContent = client.getContent();
            Map<String, String> mapContent = WXPayUtil.xmlToMap(xmlContent);

            //封装返回结果集
            Map result = new HashMap();
            result.put("out_trade_no", orderNo);
            result.put("course_id", order.getCourseId());
            result.put("total_fee", order.getTotalFee());
            result.put("result_code", mapContent.get("result_code"));
            result.put("code_url", mapContent.get("code_url"));

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20001, "生成微信支付码失败！");

        }

    }

    /**
     * 查询微信支付状态
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, String> getPaymentStatus(String orderNo) {
        //封装参数
        Map m = new HashMap();
        m.put("appid", "wx74862e0dfcf69954");
        m.put("mch_id", "1558950191");
        m.put("out_trade_no", orderNo);
        m.put("nonce_str", WXPayUtil.generateNonceStr());

        try {
            //设置请求参数
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //返回第三方数据
            String xmlContent = client.getContent();
            Map<String, String> mapContent = WXPayUtil.xmlToMap(xmlContent);

            return mapContent;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20001, "获取微信支付信息失败");
        }

    }

    @Override
    public void createPaymentLog(Map<String, String> statusMap) {
        //更新订单表状态
        String orderNo = statusMap.get("out_trade_no");
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);
        if (order.getStatus().intValue() == 1){
            return;
        }
        order.setStatus(1);
        orderService.updateById(order);

        //添加支付记录表信息
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);
        payLog.setPayTime(new Date());
        payLog.setPayType(1); //1表示微信支付
        payLog.setTotalFee(order.getTotalFee());
        payLog.setTradeState(statusMap.get("trade_state"));//支付状态
        payLog.setTransactionId(statusMap.get("transaction_id"));//支付流水号
        payLog.setAttr(JSONObject.toJSONString(statusMap));
        baseMapper.insert(payLog);

    }

}

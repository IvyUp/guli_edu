package com.atguigu.orderservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.orderservice.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-11-09
 */
//@CrossOrigin
@RestController
@RequestMapping("/orderservice/paylog")
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    /**
     * 生成微信支付二维码
     * @param orderNo
     * @return
     */
    @GetMapping("/create/qr/{orderNo}")
    public R createPaymentQR(@PathVariable("orderNo") String orderNo){
       Map codeMap = payLogService.createPaymentQR(orderNo);
       return R.ok().data(codeMap);
    }

    /**
     * 获取微信支付订单状态
     * @param orderNo
     * @return
     */
    @GetMapping("/status/{orderNo}")
    public R getPaymentStatus(@PathVariable("orderNo") String orderNo){
        Map<String, String> statusMap = payLogService.getPaymentStatus(orderNo);
        if (statusMap == null){
            return R.error().message("支付出错");
        }
        //支付成功
        if (statusMap.get("trade_state").equals("SUCCESS")){
            payLogService.createPaymentLog(statusMap);
            return R.ok().message("支付成功");
        }

        return R.ok().code(25000).message("支付中");
    }






}


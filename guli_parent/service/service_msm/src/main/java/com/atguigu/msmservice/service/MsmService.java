package com.atguigu.msmservice.service;

import java.util.Map;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-07 11:14
 */
public interface MsmService {

    //向用户手机，发送验证码短信
    Boolean send(String phone, String templateCode, Map<String, Object> param);
}

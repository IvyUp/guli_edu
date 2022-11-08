package com.atguigu.ucentermember.service;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-08 14:47
 */
public interface WxApiService {

    //获取微信access_token和openid
    String getAccessTokenInfo(String code);

    //获取微信用户信息
    String getUserInfo(String accessToken, String openid);

    //获取微信扫描url
    String getLoginUrl();
}

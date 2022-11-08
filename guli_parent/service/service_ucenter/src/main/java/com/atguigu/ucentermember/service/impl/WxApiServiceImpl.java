package com.atguigu.ucentermember.service.impl;

import com.atguigu.servicebase.exception.MyException;
import com.atguigu.ucentermember.service.WxApiService;
import com.atguigu.ucentermember.util.ConstantUtil;
import com.atguigu.ucentermember.util.HttpClientUtil;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-08 14:47
 */
@Service
public class WxApiServiceImpl implements WxApiService {

    /**
     * 获取微信扫码用户的access_token和openId
     * @param code
     * @return
     */
    @Override
    public String getAccessTokenInfo(String code) {
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantUtil.WX_OPEN_APP_ID,
                ConstantUtil.WX_OPEN_APP_SECRET,
                code);

        try {
            return HttpClientUtil.get(accessTokenUrl);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20001, "获取微信access_token和openId失败");
        }

    }

    /**
     * 获取微信用户信息
     * @param accessToken
     * @param openid
     * @return
     */
    @Override
    public String getUserInfo(String accessToken, String openid) {
        String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=%s" +
                "&openid=%s";
        String userInfoUrl = String.format(baseUserInfoUrl,
                accessToken,
                openid);
        try {
            return HttpClientUtil.get(userInfoUrl);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20002, "获取微信用户信息失败");
        }

    }

    /**
     * 获取微信扫描url
     * @return 微信二维码
     */
    @Override
    public String getLoginUrl() {
        //微信开放平台授权baseUrl，%s相当于？代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //对redirect_url进行URLEncoder编码
        String redirectUrl = ConstantUtil.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl  = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //设置%s里面值
        String LoginUrl = String.format(
                baseUrl,
                ConstantUtil.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu");

        return LoginUrl;
    }

}

package com.atguigu.ucentermember.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.ucentermember.entity.UcenterMember;
import com.atguigu.ucentermember.service.UcenterMemberService;
import com.atguigu.ucentermember.service.WxApiService;
import com.atguigu.ucentermember.util.ConstantUtil;
import com.atguigu.ucentermember.util.HttpClientUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @Description：微信扫码登录接口
 * @Author：Ivy_up
 * @Create：2022-11-08 10:38
 */
@Controller
//@CrossOrigin
@RequestMapping("/educenter/wx")
public class WxApiController {

    @Autowired
    private WxApiService wxApiService;

    @Autowired
    private UcenterMemberService memberService;

    @GetMapping("/callback")
    public String callback(String code, String state){
        // 获取到临时票据code
        //System.out.println("code =" + code);
        //System.out.println("state = " + state);

        // 使用code，获取微信access_token和openid
        String accessTokenInfo = wxApiService.getAccessTokenInfo(code);
        // 取出字符串中的access_token和openid
        Gson gson = new Gson();
        HashMap accessTokenMap = gson.fromJson(accessTokenInfo, HashMap.class);
        String accessToken = (String) accessTokenMap.get("access_token");
        String openid = (String) accessTokenMap.get("openid");

        // 判断此openid用户是否存在
        UcenterMember member = memberService.getMemberByOpenid(openid);

        if (member == null){
            // 请求微信，获取用户信息
            String userInfo = wxApiService.getUserInfo(accessToken, openid);
            HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
            String nickname = (String) userInfoMap.get("nickname"); //用户名
            String headimgurl = (String) userInfoMap.get("headimgurl"); // 头像

            // 将用户信息保存到数据库
            member = new UcenterMember();
            member.setOpenid(openid);
            member.setNickname(nickname);
            member.setAvatar(headimgurl);
            memberService.save(member);
        }

        //使用jwt根据member对象，生成token字符串
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

        return "redirect:http://localhost:3000?token=" + jwtToken;
    }


    @GetMapping("/login")
    public String getWxCode(){
        String loginUrl = wxApiService.getLoginUrl();

        //重定向到请求微信地址里面
        return "redirect:" + loginUrl;
    }


}

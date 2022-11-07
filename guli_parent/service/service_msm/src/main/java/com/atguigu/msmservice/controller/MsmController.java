package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.util.RandomCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-07 11:14
 */
@RestController
@RequestMapping("/edumsm/code")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/send/{phone}")
    public R code(@PathVariable("phone") String phone){
        //判断redis是否已经存在验证码
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)){
            return R.ok();
        }

        //生成4位验证码
        code = RandomCodeUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);

        String templateCode = "SMS_154950909";
        //发送验证码短信
        Boolean isSend = msmService.send(phone, templateCode, param);

        if (!isSend){
            return R.error().message("发送短信失败");
        }
        //验证码存入Redis，有效期5分钟
        redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
        return R.ok();
    }


}

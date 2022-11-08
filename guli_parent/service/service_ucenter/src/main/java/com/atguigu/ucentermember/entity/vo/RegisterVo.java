package com.atguigu.ucentermember.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-07 14:58
 */
@Data
public class RegisterVo implements Serializable {

    private String nickname; //用户名

    private String mobile; //手机号

    private String password; //密码

    private String code; //验证码

}

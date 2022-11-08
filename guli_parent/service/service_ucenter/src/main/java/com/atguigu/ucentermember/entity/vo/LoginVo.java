package com.atguigu.ucentermember.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-07 14:56
 */
@Data
public class LoginVo implements Serializable {

    private String mobile;  //手机号

    private String password;    //登录密码

}

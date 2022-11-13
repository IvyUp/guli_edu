package com.atguigu.servicebase.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description：订单中的会员信息
 * @Author：Ivy_up
 * @Create：2022-11-09 14:59
 */
@Data
public class OrderMemberVo implements Serializable {

    private String memberId;

    private String nickname;

    private String mobile;

}

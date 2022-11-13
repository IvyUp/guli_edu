package com.atguigu.ucentermember.service;

import com.atguigu.ucentermember.entity.UcenterMember;
import com.atguigu.ucentermember.entity.vo.LoginVo;
import com.atguigu.ucentermember.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-11-07
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    //用户登录
    String login(LoginVo loginVo);

    //用户注册
    void register(RegisterVo registerVo);

    //根据openid查询用户
    UcenterMember getMemberByOpenid(String openid);

    //每天注册人数
    Integer getDailyRegisterCount(String date);
}

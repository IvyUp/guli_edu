package com.atguigu.ucentermember.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.vo.OrderMemberVo;
import com.atguigu.ucentermember.entity.UcenterMember;
import com.atguigu.ucentermember.entity.vo.LoginInfoVo;
import com.atguigu.ucentermember.entity.vo.LoginVo;
import com.atguigu.ucentermember.entity.vo.RegisterVo;
import com.atguigu.ucentermember.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-11-07
 */
//@CrossOrigin
@RestController
@RequestMapping("/educenter/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    /**
     * 用户登录
     * @param loginVo
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo){
        String token = memberService.login(loginVo);
        return R.ok().data("token", token);
    }

    /**
     * 用户注册
     * @param registerVo
     * @return
     */
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo){
        if (registerVo == null){
            return R.error();
        }
        memberService.register(registerVo);
        return R.ok();
    }

    /**
     * 获取用户登录信息
     * @return
     */
    @GetMapping("/login/info")
    public R getLoginInfo(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(memberId);
        LoginInfoVo loginInfoVo = new LoginInfoVo();
        BeanUtils.copyProperties(member, loginInfoVo);
        return R.ok().data("items", loginInfoVo);
    }

    /**
     * 获取订单中的会员信息
     * @param memberId
     * @return
     */
    @GetMapping("/order/{memberId}")
    public OrderMemberVo getOrderMemberInfo(@PathVariable("memberId") String memberId){
        UcenterMember member = memberService.getById(memberId);
        OrderMemberVo omo = new OrderMemberVo();
        omo.setMemberId(memberId);
        omo.setNickname(member.getNickname());
        omo.setMobile(member.getMobile());
        return omo;
    }

    /**
     * 获取每天注册人数
     * @param date 日期
     * @return
     */
    @GetMapping("/statistics/{date}")
    public Integer getDailyRegisterCount(@PathVariable("date") String date){
        return memberService.getDailyRegisterCount(date);
    }


}


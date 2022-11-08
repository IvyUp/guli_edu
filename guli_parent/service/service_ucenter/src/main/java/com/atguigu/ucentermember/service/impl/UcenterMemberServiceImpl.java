package com.atguigu.ucentermember.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.servicebase.exception.MyException;
import com.atguigu.ucentermember.entity.UcenterMember;
import com.atguigu.ucentermember.entity.vo.LoginVo;
import com.atguigu.ucentermember.entity.vo.RegisterVo;
import com.atguigu.ucentermember.mapper.UcenterMemberMapper;
import com.atguigu.ucentermember.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.*;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-11-07
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //校验参数
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new MyException(20001, "手机号不能为空");
        }
        //获取会员
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember member = baseMapper.selectOne(wrapper);
        if (member == null){
            throw new MyException(20002, "用户不存在");
        }
        //校验密码
        String encryptPassword = MD5.encrypt(password);
        if (!encryptPassword.equals(member.getPassword())){
            throw new MyException(20003, "密码错误");
        }
        //校验是否被禁用
        if (member.getIsDeleted()){
            throw new MyException(20004, "用户被封禁");
        }

        //登录成功，返回Token字符串
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());

        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String nickname = registerVo.getNickname();
        String code = registerVo.getCode();
        //校验注册信息完整性
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(code) || StringUtils.isEmpty(password)){
            throw new MyException(20001, "注册信息有误");
        }
        //检查手机号或用户名是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile).or().eq("nickname", nickname);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0){
            throw new MyException(20002, "用户已存在");
        }
        //检查验证码是否有效
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)){
            throw new MyException(20003, "验证码错误或失效");
        }

        //添加记录
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password)); //存储加密后的密码
        member.setAvatar("http://localhost:8006/pig.jpg");
        int result = baseMapper.insert(member);

        if (result <= 0){
            throw new MyException(20004, "注册用户失败");
        }

    }
}

package com.atguigu.ucentermember.mapper;

import com.atguigu.ucentermember.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2022-11-07
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    //每天注册人数
    Integer getDailyRegisterCount(@Param("date") String date);

}

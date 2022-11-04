package com.atguigu.cmsservice.service.impl;

import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.mapper.CrmBannerMapper;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-11-04
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    /**
     * 获取id后3位的banner
     * @return
     */
    @Override
    public List<CrmBanner> getBanner() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper();
        wrapper.orderByDesc("id").last("limit 3");
        List<CrmBanner> bannerList = baseMapper.selectList(wrapper);
        return bannerList;
    }

}

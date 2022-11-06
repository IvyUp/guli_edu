package com.atguigu.cmsservice.service.impl;

import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.mapper.CrmBannerMapper;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
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
     * @Cacheable 根据方法对其返回结果进行缓存，下次请求时，如果缓存存在，则直接读取缓存数据返回；
     *  如果缓存不存在，则执行方法，并把返回的结果存入缓存中。一般用在查询方法上
     *      value：缓存名
     *      key
     */
    @Override
    @Cacheable(value = "banner", key = "'selectIndexList'")
    public List<CrmBanner> getBanner() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper();
        wrapper.orderByDesc("id").last("limit 3");
        List<CrmBanner> bannerList = baseMapper.selectList(wrapper);
        return bannerList;
    }

}

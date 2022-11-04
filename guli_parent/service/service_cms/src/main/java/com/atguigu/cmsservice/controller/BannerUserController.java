package com.atguigu.cmsservice.controller;

import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.atguigu.commonutils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * banner用户接口
 */
@CrossOrigin
@RestController
@RequestMapping("/user/cmsservice/banner")
public class BannerUserController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 获取id后3位的banner
     * @return
     */
    @GetMapping("get")
    public R getBanner(){
        List<CrmBanner> bannerList = bannerService.getBanner();
        return R.ok().data("items",bannerList);
    }



}

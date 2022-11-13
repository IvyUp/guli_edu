package com.atguigu.cmsservice.controller;

import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.atguigu.commonutils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * banner用户接口
 */
//@CrossOrigin
@RestController
@RequestMapping("/educms/user/banner")
public class BannerUserController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 获取id后3位的banner
     * @return
     */
    @GetMapping("/get")
    public R getBanner(){
        List<CrmBanner> bannerList = bannerService.getBanner();
        return R.ok().data("items",bannerList);
    }



    /**
     * 添加banner
     * @param banner
     * @return
     */
    @PostMapping("/add")
    public R addBanner(@RequestBody CrmBanner banner){
        bannerService.save(banner);
        return R.ok();
    }

}

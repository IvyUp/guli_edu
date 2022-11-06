package com.atguigu.cmsservice.controller;


import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.atguigu.commonutils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * banner管理员接口
 * </p>
 *
 * @author atguigu
 * @since 2022-11-04
 */
@CrossOrigin
@RestController
@RequestMapping("/admin/educms/banner")
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 查询所有Banner，并分页
     * @param current 当前页
     * @param size 每页banner数
     * @return
     */
    @GetMapping("/page/{current}/{size}")
    public R getBannerPage(@PathVariable("current") Long current,
                           @PathVariable("size") Long size){
        Page<CrmBanner> page = new Page<>(current, size);
        bannerService.page(page, null);
        long total = page.getTotal();
        List<CrmBanner> records = page.getRecords();
        return R.ok().data("total",total).data("records",records);
    }

//    /**
//     * 添加banner
//     * @param banner
//     * @return
//     */
//    @PostMapping("/add")
//    public R addBanner(@RequestBody CrmBanner banner){
//        bannerService.save(banner);
//        return R.ok();
//    }


    @GetMapping("/get/{id}")
    public R getBannerById(@PathVariable("id") String id){
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("items",banner);
    }

}


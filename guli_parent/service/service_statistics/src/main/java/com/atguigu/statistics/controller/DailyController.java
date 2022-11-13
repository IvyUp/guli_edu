package com.atguigu.statistics.controller;


import com.atguigu.commonutils.R;
import com.atguigu.statistics.client.MemberClient;
import com.atguigu.statistics.entity.Daily;
import com.atguigu.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-11-11
 */
//@CrossOrigin
@RestController
@RequestMapping("/statisticsservice/daily")
public class DailyController {

    @Autowired
    private DailyService dailyService;

    /**
     * 添加每日统计数据
     * @param date
     * @return
     */
    @PutMapping("/{date}")
    public R createDailyStatistics(@PathVariable("date") String date){
        dailyService.createDailyStatistics(date);
        return R.ok();
    }

    /**
     * 获取echarts数据
     * @return
     */
    @GetMapping("/echarts/{type}/{begin}/{end}")
    public R getEchartsData(@PathVariable("type") String type,
                            @PathVariable("begin") String begin,
                            @PathVariable("end") String end){
        Map<String, Object> map = dailyService.getEchartsData(type, begin, end);
        return R.ok().data(map);
    }



}


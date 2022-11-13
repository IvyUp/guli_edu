package com.atguigu.statistics.service;

import com.atguigu.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-11-11
 */
public interface DailyService extends IService<Daily> {

    //添加每日统计数据
    void createDailyStatistics(String date);

    //获取Echarts数据
    Map<String, Object> getEchartsData(String type, String begin, String end);
}

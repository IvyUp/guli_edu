package com.atguigu.statistics.service.impl;

import com.atguigu.servicebase.exception.MyException;
import com.atguigu.statistics.client.MemberClient;
import com.atguigu.statistics.entity.Daily;
import com.atguigu.statistics.mapper.DailyMapper;
import com.atguigu.statistics.service.DailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-11-11
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private MemberClient memberClient;

    /**
     * 创建每天的统计数据
     * @param date 日期
     */
    @Override
    public void createDailyStatistics(String date) {
        Daily daily = new Daily();

        daily.setDateCalculated(date);
        daily.setRegisterNum(memberClient.getDailyRegisterCount(date));
        daily.setLoginNum(new RandomUtils().nextInt(100, 200));
        daily.setVideoViewNum(new RandomUtils().nextInt(100, 200));
        daily.setCourseNum(new RandomUtils().nextInt(100, 200));

        boolean result = this.saveOrUpdate(daily);

        if (!result){
            throw new MyException(20001, "保存每日统计数据失败！");
        }

    }

    /**
     * 获取指定时间范围内，指定类型的统计数据
     * @param type 统计类型
     * @param begin 开始时间
     * @param end 结束时间
     * @return
     */
    @Override
    public Map<String, Object> getEchartsData(String type, String begin, String end) {
        //查询需要的数据
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated",type);
        List<Daily> dailyList = baseMapper.selectList(wrapper);

        //封装数据
        List<String> dateCalculatedList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        for (Daily daily : dailyList) {
            //封装日期数据
            dateCalculatedList.add(daily.getDateCalculated());
            //根据选择的统计类型，封装统计数据
            switch (type){
                case "login_num":
                    countList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    countList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    countList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    countList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("dateCalculatedList",dateCalculatedList);
        dataMap.put("countList",countList);

        return dataMap;
    }
}

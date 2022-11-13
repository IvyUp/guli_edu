package com.atguigu.statistics.schedule;

import com.atguigu.statistics.service.DailyService;
import com.atguigu.statistics.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;

/**
 * @Description：定时任务类
 * @Author：Ivy_up
 * @Create：2022-11-11 14:30
 */
@Component
public class ScheduledTask {

    @Autowired
    private DailyService dailyService;

    /**
     * 每天凌晨1点，执行定时任务
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void scheduledTask(){
        //获取前一天的日期
        String date = DateUtil.formatDate( DateUtil.addDays(new Date(), -1) );
        dailyService.createDailyStatistics(date);
    }


}

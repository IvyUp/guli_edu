package com.atguigu.eduservice.feign;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-04 15:31
 */
@Component
@FeignClient(name = "service-vod", fallback = VodFeignFallback.class)
public interface VodFeign {

    /**
     * 根据视频id，删除视频
     * @param videoId 视频id
     * @return
     */
    @DeleteMapping("/eduvod/video/delete/{videoId}") //注意使用完整路径
    public R deleteVideoById(@PathVariable("videoId") String videoId);

    @DeleteMapping("/eduvod/video/delete/batch")
    public R deleteVideoBatchByIds(@RequestParam List<String> videoIds);

}

package com.atguigu.eduservice.feign;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-04 19:52
 */
@Component
public class VodFeignFallback implements VodFeign{
    @Override
    public R deleteVideoById(String videoId) {
        return R.error().message("方法异常，熔断器启动......");
    }

    @Override
    public R deleteVideoBatchByIds(List<String> videoIds) {
        return R.error().message("方法异常，熔断器启动......");
    }
}

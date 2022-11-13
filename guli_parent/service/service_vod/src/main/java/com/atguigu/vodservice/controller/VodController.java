package com.atguigu.vodservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.vodservice.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-03 20:12
 */
@RestController
@RequestMapping("/eduvod/video")
//@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    /**
     * 上传视频到阿里云
     * @param file 视频
     * @return 阿里云返回的视频id
     */
    @PostMapping("/upload")
    public R uploadVod(@RequestBody MultipartFile file){
        String videoId = vodService.uploadVod(file);
        return R.ok().data("item", videoId);
    }

    /**
     * 根据视频id，删除视频
     * @param videoId 视频id
     * @return
     */
    @DeleteMapping("/delete/{videoId}")
    public R deleteVideoById(@PathVariable String videoId){
        vodService.deleteVideoById(videoId);
        return R.ok();
    }

    /**
     * 根据视频id，批量删除视频
     * @param videoIds
     * @return
     */
    @DeleteMapping("/delete/batch")
    public R deleteVideoBatchByIds(@RequestParam List<String> videoIds){
        vodService.deleteVideoBatchByIds(videoIds);
        return R.ok();
    }

    /**
     *
     * @param videoId
     * @return 阿里云视频播放凭证
     */
    @GetMapping("/playauth/{videoId}")
    public R getVideoPlayAuth(@PathVariable("videoId") String videoId){
        String playAuth = vodService.getVideoPlayAuth(videoId);
        return R.ok().data("playAuth",playAuth);
    }


}

package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.video.VideoInfoForm;
import com.atguigu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-11-01
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    /**
     * 新增小节
     * @param video
     * @return
     */
    @PostMapping("/add")
    public R addVideo(@RequestBody EduVideo video){
        videoService.save(video);
        return R.ok();
    }

    /**
     * 修改小节
     * @param videoId 小节id
     * @param videoInfoForm
     * @return
     */
    @PutMapping("/update/{videoId}")
    public R updateVideoById(@PathVariable("videoId") String videoId,
                             @RequestBody VideoInfoForm videoInfoForm){
        videoService.updateVideoInfoById(videoInfoForm);
        return R.ok();
    }

    /**
     * 根据id删除小节
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public R deleteVideoById(@PathVariable String id){
        videoService.removeById(id);
        return R.ok();
    }

    /**
     * 根据id，查询小节数据
     * @param id 小节id
     * @return
     */
    @GetMapping("/get/{id}")
    public R getVideoById(@PathVariable String id){
        EduVideo video = videoService.getById(id);
        return R.ok().data("items", video);
    }

}


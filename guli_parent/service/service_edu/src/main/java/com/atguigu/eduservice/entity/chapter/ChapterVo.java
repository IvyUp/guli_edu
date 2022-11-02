package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

import java.util.List;

/**
 * @Description：返回前端的课程章节数据
 * @Author：Ivy_up
 * @Create：2022-11-02 10:40
 */
@Data
public class ChapterVo {
    //课程章节id
    private String id;

    //课程章节名称
    private String title;

    //本课程章节的所有课程小节
    private List<VideoVo> children;
}

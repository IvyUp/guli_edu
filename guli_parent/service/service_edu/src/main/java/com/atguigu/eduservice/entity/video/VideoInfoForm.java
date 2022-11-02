package com.atguigu.eduservice.entity.video;

import lombok.Data;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-02 23:55
 */
@Data
public class VideoInfoForm {

    private String id;

    private String courseId;

    private String chapterId;

    private String title;

    private Integer sort;

    private Boolean free;

}

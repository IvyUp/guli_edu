package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-11-01
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    @GetMapping("/list/{courseId}")
    public R getChapterList(@PathVariable("courseId") String courseId){
        List<ChapterVo> chapterVoList = chapterService.getChapterList(courseId);
        return R.ok().data("items", chapterVoList);
    }

    /**
     * 添加章节
     * @param chapter 章节对象
     * @return
     */
    @PostMapping("/add")
    public R addChapter(@RequestBody EduChapter chapter){
        boolean result = chapterService.save(chapter);
        if (result){
            return R.ok().message("添加章节成功");
        }else {
            return R.error().message("添加章节失败");
        }
    }

    /**
     * 根据章节id，查询章节信息
     * @param chapterId 章节id
     * @return 章节对象
     */
    @GetMapping("/get/{chapterId}")
    public R getChapterbyId(@PathVariable String chapterId){
        EduChapter chapter = chapterService.getById(chapterId);
        return R.ok().data("items",chapter);
    }

    /**
     * 修改章节信息
     * @param chapter
     * @return
     */
    @PutMapping("/update/{chapterId}")
    public R updateChapter(@PathVariable String chapterId,
                            @RequestBody EduChapter chapter){
        chapter.setId(chapterId);
        chapterService.updateById(chapter);
        return R.ok();
    }

    /**
     * 根据章节id，删除章节
     * @param chapterId
     * @return
     */
    @DeleteMapping("/delete/{chapterId}")
    public R deleteChapterById(@PathVariable String chapterId){
        boolean result = chapterService.deleteChapterById(chapterId);
        if (result){
            return R.ok().message("删除章节信息成功");
        }else {
            return R.error().message("删除章节信息失败");
        }
    }
}


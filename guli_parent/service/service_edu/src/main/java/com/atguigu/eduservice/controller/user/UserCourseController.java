package com.atguigu.eduservice.controller.user;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.vo.CourseDetailVo;
import com.atguigu.eduservice.entity.vo.CourseQueryVo;
import com.atguigu.eduservice.feign.OrderClient;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.vo.OrderCourseVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-11-05 0:05
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/user/course")
public class UserCourseController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    /**
     * 获取id排名后8位的课程
     * @return
     */
    @GetMapping("/get")
    public R getCourse(){
        List<EduCourse> courseList = courseService.getCourse();
        return R.ok().data("items",courseList);
    }

    /**
     *
     * @param page 当前页
     * @param limit 每页记录数
     * @param courseQueryVo 课程条件
     * @return
     */
    @PostMapping("/page/{page}/{limit}")
    public R getCoursePageByVo(@PathVariable long page,
                               @PathVariable long limit,
                               @RequestBody(required = false) CourseQueryVo courseQueryVo){
        Page<EduCourse> coursePage = new Page<>(page, limit);
        Map<String, Object> courseMap = courseService.getCoursePageByVo(coursePage, courseQueryVo);
        return R.ok().data(courseMap);
    }

    /**
     *
     * @param courseId 课程id
     * @return 课程详细信息
     */
    @GetMapping("/detail/{courseId}")
    public R getCourseDetailVoById(@PathVariable String courseId, HttpServletRequest request){
        //1 查询课程详细信息
        CourseDetailVo courseDetailVo = courseService.getCourseDetailVoById(courseId);
        //2 查询课程章节信息
        List<ChapterVo> chapterVoList = chapterService.getChapterList(courseId);
        //3 查询课程购买信息
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        Boolean isBuyCourse  = false;
        if (!StringUtils.isEmpty(memberId)) {
            isBuyCourse = orderClient.isBuyCourse(courseId, memberId);
        }
        return R.ok().data("courseDetailVo",courseDetailVo)
                .data("chapterVoList",chapterVoList)
                .data("isBuyCourse",isBuyCourse);
    }

    /**
     * 获取订单中需要的课程信息
     * @param courseId
     * @return
     */
    @GetMapping("/order/{courseId}")
    public OrderCourseVo getOrderCourseInfo(@PathVariable("courseId") String courseId){
        OrderCourseVo orderCourseVo = courseService.getOrderCourseInfo(courseId);
        return orderCourseVo;
    }

}

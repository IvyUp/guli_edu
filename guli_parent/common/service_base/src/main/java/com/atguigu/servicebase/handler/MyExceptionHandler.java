package com.atguigu.servicebase.handler;



import com.atguigu.commonutils.ExceptionUtil;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description：统一异常处理类
 * @Author：Ivy_up
 * @Create：2022-10-27 19:24
 */
@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

    /**
     * 全局统一异常处理方法
     * @param e 所有异常
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("全局异常处理方法");
    }

    /**
     * 特定异常处理方法
     * @param e 算术异常
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        log.error(e.getMessage());
        return R.error().message("ArithmeticException特定异常处理方法");
    }

    /**
     * 自定义异常处理方法
     * @param e 自定义异常
     * @return
     */
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public R error(MyException e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return R.error().code(e.getCode()).message(e.getMsg());
    }

}

package com.atguigu.servicebase.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description：自定义异常类
 * @Author：Ivy_up
 * @Create：2022-10-27 19:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyException extends RuntimeException{

    //异常状态码
    private Integer code;

    //异常信息
    private String msg;
}

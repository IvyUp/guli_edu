package com.atguigu.eduservice.easyExcel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description：
 * @Author：Ivy_up
 * @Create：2022-10-31 20:26
 */
public class TestEasyExcel {

    /**
     * EasyExcel读操作
     */
    @Test
    public void read(){
        String fileName = "E:\\easyExcel.xlsx";
        //需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, DemoData.class, new ExcelListener()).sheet().doRead();
    }

    /**
     * EasyExcel写操作
     */
    @Test
    public void write(){
        String fileName = "E:\\easyExcel.xlsx";

        //需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoData.class).sheet("学生信息").doWrite(getData());

    }

    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("张" + i);
            list.add(data);
        }
        return list;
    }

}

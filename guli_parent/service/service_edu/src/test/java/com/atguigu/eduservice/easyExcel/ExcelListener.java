package com.atguigu.eduservice.easyExcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @Description：excel监听器
 * @Author：Ivy_up
 * @Create：2022-10-31 20:41
 */
public class ExcelListener extends AnalysisEventListener<DemoData> {


    /**
     * 一行一行读取excel内容
     * @param data
     * @param analysisContext
     */
    @Override
    public void invoke(DemoData data, AnalysisContext analysisContext) {
        System.out.println("********" + data);
    }

    /**
     * 读取excel表头信息
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息" + headMap);
    }


    /**
     * 读取完成后执行
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

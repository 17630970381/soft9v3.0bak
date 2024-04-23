package com.cqupt.software_9.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.cqupt.software_9.service.Adapter.RuntimeServiceTaskAdapter;
import com.cqupt.software_9.service.Request.RuntimeTaskRequest;
import com.cqupt.software_9.service.Response.RuntimeTaskResponse;
import com.cqupt.software_9.tool.PythonRun;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class RuntimeTaskServiceImpl extends RuntimeServiceTaskAdapter {

    @Resource
    private PythonRun pythonRun;

   /* @Resource(name = "dataTableDataSourceProperties")
    private DataSourceProperties dataTableDataSourceProperties;*/

    @Override
    public RuntimeTaskResponse submitTask(RuntimeTaskRequest request) throws Exception {
        RuntimeTaskResponse taskResponse = new RuntimeTaskResponse();
        BeanUtils.copyProperties(request, taskResponse);
        taskResponse.setTaskStartTime(new Date().getTime());
        System.out.println("接收到的PyPath" + request.getPyPath());
        System.out.println("接收到的Args" + request.getArgs());
        //任务信息入库
        //提交任务
        taskResponse.setTaskFinishTime(new Date().getTime());
        String a1 = pythonRun.run(request.getPyPath(), request.getArgs());
        System.out.println("a1=" + a1);
        List<String> res = JSONObject.parseArray(pythonRun.run(request.getPyPath(), request.getArgs()), String.class);
        taskResponse.setRes(res);
        System.out.println("res" + res);
        return taskResponse;
    }

    @Override
    public RuntimeTaskResponse submitTask1(RuntimeTaskRequest request) throws Exception {
        //System.out.println(request+"1");
        RuntimeTaskResponse taskResponse = new RuntimeTaskResponse();
        BeanUtils.copyProperties(request, taskResponse);
        taskResponse.setTaskStartTime(new Date().getTime());
        System.out.println("接收到的PyPath" + request.getPyPath());
        System.out.println("接收到的request" + request.getRequest());
        //任务信息入库
        //提交任务
        taskResponse.setTaskFinishTime(new Date().getTime());
        String a1 = pythonRun.run1(request.getPyPath(), request.getRequest());
        System.out.println("a1=" + a1);
        List<String> res = JSONObject.parseArray(pythonRun.run1(request.getPyPath(), request.getRequest()), String.class);
        taskResponse.setRes(res);
        System.out.println("res" + res);
        return taskResponse;
    }

    @Override
    public RuntimeTaskResponse submitTask2(RuntimeTaskRequest request) throws Exception {
        //System.out.println(request+"1");
        RuntimeTaskResponse taskResponse = new RuntimeTaskResponse();
        BeanUtils.copyProperties(request, taskResponse);
        taskResponse.setTaskStartTime(new Date().getTime());
        System.out.println("接收到的PyPath" + request.getPyPath());
        System.out.println("接收到的request" + request.getRequest());
        //任务信息入库
        //提交任务
        taskResponse.setTaskFinishTime(new Date().getTime());
        String a1 = pythonRun.run2(request.getPyPath(), request.getRequest());
        System.out.println("a1=" + a1);
        List<String> res = JSONObject.parseArray(pythonRun.run2(request.getPyPath(), request.getRequest()), String.class);
        taskResponse.setRes(res);
        System.out.println("res" + res);
        return taskResponse;
    }
}

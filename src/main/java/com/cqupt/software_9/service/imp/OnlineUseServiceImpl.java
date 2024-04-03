package com.cqupt.software_9.service.imp;

import com.cqupt.software_9.service.Adapter.OnlineUseServiceAdapter;
import com.cqupt.software_9.service.Request.RuntimeTaskRequest;
import com.cqupt.software_9.service.Request.onlineUse;
import com.cqupt.software_9.service.Response.OnlineServiceResponse;
import com.cqupt.software_9.service.Response.RuntimeTaskResponse;
import com.cqupt.software_9.service.RuntimeTaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
public class OnlineUseServiceImpl extends OnlineUseServiceAdapter {
    @Resource
    private RuntimeTaskService runtimeTaskService;


    @Override
    public OnlineServiceResponse onlineUse(onlineUse request) throws Exception {
        OnlineServiceResponse response=new OnlineServiceResponse();
        BeanUtils.copyProperties(request,response);
        List<String> args=new LinkedList<>();
        List<String> feat = new LinkedList<>();
        String[] fea = request.getFea();
        for (String feas : fea) {
            feat.add(feas);
        }
// 将字符串列表用空格连接成一个完整的参数字符串
        String feature = "--feature=" + String.join(" ", feat);
// 然后将这个参数字符串作为一个整体添加到 args 中
        args.add(feature);
        args.add("--model_file_path="+request.getPath());
        System.out.println(args);
        RuntimeTaskRequest runtimeTaskRequest=new RuntimeTaskRequest();
//        runtimeTaskRequest.setPyPath("F:/code/Online training/Projection-test.py");
//        runtimeTaskRequest.setPyPath("/home/data/WorkSpace/software9/Arithmetic/Online/Projection-test.py");
        runtimeTaskRequest.setPyPath("E:\\soft\\software9-3\\software9\\src\\main\\resources\\Algorithm\\python\\reuse.py");

        runtimeTaskRequest.setArgs(args);
        RuntimeTaskResponse taskResponse=runtimeTaskService.submitTask(runtimeTaskRequest);
        response.setRes(taskResponse.getRes());
        return response;
    }

    @Override
    public OnlineServiceResponse useMulti(onlineUse request) throws Exception {
        OnlineServiceResponse response=new OnlineServiceResponse();
        BeanUtils.copyProperties(request,response);
        List<String> args=new LinkedList<>();
        List<String> feat = new LinkedList<>();
        String[] fea = request.getFea();
        for (String feas : fea) {
            feat.add(feas);
        }
// 将字符串列表用空格连接成一个完整的参数字符串
        String feature = "--feature=" + String.join(" ", feat);
// 然后将这个参数字符串作为一个整体添加到 args 中
        args.add(feature);
        args.add("--model_file_path="+request.getPath());
        System.out.println(args);
        RuntimeTaskRequest runtimeTaskRequest=new RuntimeTaskRequest();
//        runtimeTaskRequest.setPyPath("F:/code/Online training/Projection-test.py");
//        runtimeTaskRequest.setPyPath("/home/data/WorkSpace/software9/Arithmetic/Online/Projection-test.py");
        runtimeTaskRequest.setPyPath("E:\\soft\\software9-3\\software9\\src\\main\\resources\\Algorithm\\python\\multiDisease.py");

        runtimeTaskRequest.setArgs(args);
        RuntimeTaskResponse taskResponse=runtimeTaskService.submitTask(runtimeTaskRequest);
        response.setRes(taskResponse.getRes());
        return response;
    }
}

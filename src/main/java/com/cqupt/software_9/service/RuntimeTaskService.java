package com.cqupt.software_9.service;

import com.cqupt.software_9.service.Request.RuntimeTaskRequest;
import com.cqupt.software_9.service.Response.RuntimeTaskResponse;

import java.util.List;

public interface RuntimeTaskService {
    RuntimeTaskResponse submitTask(RuntimeTaskRequest request) throws Exception;

    RuntimeTaskResponse submitTask1(RuntimeTaskRequest request) throws Exception;


    void killTask();

    RuntimeTaskResponse queryById(Integer id);

    List<RuntimeTaskResponse> queryByBizId(Integer bizId);

    List<RuntimeTaskResponse> queryByTaskTypeAndBizIdList(String taskType, Integer bizId);

    RuntimeTaskResponse submitTask2(RuntimeTaskRequest runtimeTaskRequest) throws Exception;
}

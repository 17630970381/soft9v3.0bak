package com.cqupt.software_9.service.Adapter;

import com.cqupt.software_9.service.Request.RuntimeTaskRequest;
import com.cqupt.software_9.service.Response.RuntimeTaskResponse;
import com.cqupt.software_9.service.RuntimeTaskService;

import java.util.List;

public class RuntimeServiceTaskAdapter implements RuntimeTaskService {
    @Override
    public RuntimeTaskResponse submitTask(RuntimeTaskRequest request) throws Exception {
        return null;
    }
    @Override
    public RuntimeTaskResponse submitTask1(RuntimeTaskRequest request) throws Exception {
        return null;
    }



    @Override
    public void killTask() {

    }

    @Override
    public RuntimeTaskResponse queryById(Integer id) {
        return null;
    }

    @Override
    public List<RuntimeTaskResponse> queryByBizId(Integer bizId) {
        return null;
    }

    @Override
    public List<RuntimeTaskResponse> queryByTaskTypeAndBizIdList(String taskType, Integer bizId) {
        return null;
    }

    @Override
    public RuntimeTaskResponse submitTask2(RuntimeTaskRequest runtimeTaskRequest) throws Exception {
        return null;
    }
}

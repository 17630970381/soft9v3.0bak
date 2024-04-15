package com.cqupt.software_9.service.Adapter;

import com.cqupt.software_9.entity.history;
import com.cqupt.software_9.service.OnlineUseService;
import com.cqupt.software_9.service.Request.onlineUse;
import com.cqupt.software_9.service.Response.OnlineServiceResponse;

public class OnlineUseServiceAdapter implements OnlineUseService {


    @Override
    public OnlineServiceResponse onlineUse(onlineUse request) throws Exception {
        return null;
    }

    @Override
    public OnlineServiceResponse useMulti(onlineUse request) throws Exception {
        return null;
    }

    @Override
    public OnlineServiceResponse history(history request) throws Exception {
        return null;
    }

    @Override
    public OnlineServiceResponse historySolo(history request) throws Exception {
        return null;
    }
}

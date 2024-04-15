package com.cqupt.software_9.service;

import com.cqupt.software_9.entity.history;
import com.cqupt.software_9.service.Request.onlineUse;
import com.cqupt.software_9.service.Response.OnlineServiceResponse;

public interface OnlineUseService {
   OnlineServiceResponse onlineUse(onlineUse request)throws Exception;

   OnlineServiceResponse useMulti(onlineUse request) throws Exception;

   OnlineServiceResponse history(history request) throws Exception;

   OnlineServiceResponse historySolo(history request) throws Exception;
}

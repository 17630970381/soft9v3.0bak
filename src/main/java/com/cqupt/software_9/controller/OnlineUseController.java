package com.cqupt.software_9.controller;

import com.cqupt.software_9.entity.history;
import com.cqupt.software_9.service.OnlineUseService;
import com.cqupt.software_9.service.Request.onlineUse;
import com.cqupt.software_9.service.Response.OnlineServiceResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Log4j2
@RestController
@RequestMapping("/OnlineUse")
@CrossOrigin
public class OnlineUseController {
//    @Resource
//    private Online_randforService online_randforService;
//
//    /**
//     * 在线训练_随机森林
//     * @RequestBody request
//     * @return
//     */
//
//    @PostMapping("/submit")
//    public OnlineServiceResponse busSubmit(@RequestBody RuntimeBusCreateRequest_online_RandFor request) throws Exception {
//        try {
//            OnlineServiceResponse result = online_randforService.online_Randfor(request);
//            return result;
//        } catch (Exception e) {
//            OnlineServiceResponse res = new OnlineServiceResponse();
//            res.setE(e);
//            return res;
//        }
//    }



    @Resource
    private OnlineUseService onlineUseService;


    @PostMapping("/use")
    public OnlineServiceResponse OnlineUse(@RequestBody onlineUse request) throws Exception {

//        try {
//            OnlineServiceResponse res = onlineUseService.onlineUse(request);
//            return res;
//        } catch (Exception e) {
//            OnlineServiceResponse res = new OnlineServiceResponse();
//            res.setE(e);
//            return res;
//        }
        OnlineServiceResponse res = onlineUseService.onlineUse(request);
        return res;
    }


    @PostMapping("/useMulti")
    public OnlineServiceResponse useMulti(@RequestBody onlineUse request) throws Exception {
        OnlineServiceResponse res = onlineUseService.useMulti(request);
        return res;
    }

    @PostMapping("/history")
    public OnlineServiceResponse history(@RequestBody history request) throws Exception {
        OnlineServiceResponse res = onlineUseService.history(request);
        return res;
    }

    @PostMapping("/historySolo")
    public OnlineServiceResponse historySolo(@RequestBody history request) throws Exception {
        OnlineServiceResponse res = onlineUseService.historySolo(request);
        return res;
    }




}

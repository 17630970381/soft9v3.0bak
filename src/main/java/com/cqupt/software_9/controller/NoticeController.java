package com.cqupt.software_9.controller;


import com.cqupt.software_9.common.R;
import com.cqupt.software_9.entity.Notification;
import com.cqupt.software_9.service.NoticeService;
import com.cqupt.software_9.service.UserLogService;
import com.cqupt.software_9.vo.InsertNoticeVo;
import com.cqupt.software_9.vo.Notificationtest;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private UserLogService logService;

    @GetMapping("/allNotices")
    public PageInfo<Notification> allNotices(@RequestParam Integer pageNum , @RequestParam Integer pageSize){
        return noticeService.allNotices(pageNum, pageSize);
    }

    @GetMapping("/queryNotices")
    public List<Notification> queryNotices(){
        return noticeService.queryNotices();
    }




    @PostMapping("/updateNotice")
    @Transactional
    public R updateNotice(@RequestBody Notificationtest notificationtest){
        logService.insertLog(notificationtest.getUid(), 0, notificationtest.getUsername()+"更新了一条通知");
        Notification notification = notificationtest.getNotification();
        notification.setUpdateTime(new Date());
        noticeService.saveOrUpdate(notification);

        return new R<>(200 , "成功", null);
    }


    @PostMapping("delNotice")
    @Transactional
    public R delNotice(@RequestBody Notificationtest notificationtest){
        System.out.println(notificationtest);
        logService.insertLog(notificationtest.getUid(), 0, notificationtest.getUsername()+"删除了一条"+notificationtest.getNotification().getUsername()+"发布的通知");
        noticeService.removeById(notificationtest.getNotification().getInfoId());
        return new R<>(200 , "成功", null);
    }

    @PostMapping("insertNotice")
    public R insertNotice(@RequestBody InsertNoticeVo notification){

        noticeService.saveNotification(notification);
        return new R<>(200 , "成功", null);
    }


}

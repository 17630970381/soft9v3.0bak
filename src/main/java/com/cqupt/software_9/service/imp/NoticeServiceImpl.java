package com.cqupt.software_9.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.software_9.entity.Notification;
import com.cqupt.software_9.mapper.NoticeMapper;
import com.cqupt.software_9.service.NoticeService;
import com.cqupt.software_9.service.UserLogService;
import com.cqupt.software_9.vo.InsertNoticeVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notification>
        implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private UserLogService logService;


    @Override
    public PageInfo<Notification> allNotices(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        // 返回查询结果列表
        List<Notification> notifications =  noticeMapper.selectAllNotices();
        // 使用 PageInfo 包装查询结果，并返回
        return new PageInfo<>(notifications);
    }

    @Override
    @Transactional
    public void saveNotification(InsertNoticeVo notification) {
        int  uid = notification.getUid();
        String uname = notification.getUsername();
        logService.insertLog(String.valueOf(uid), 0, uname+"发布了一条通知");
        noticeMapper.saveNotification(notification);
    }

    @Override
    public List<Notification> queryNotices() {

        List<Notification> notifications = noticeMapper.selectList(null);
        return notifications;
    }
}

package com.cqupt.software_9.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.entity.Notification;
import com.cqupt.software_9.vo.InsertNoticeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper extends BaseMapper<Notification> {


    List<Notification> selectAllNotices();

    void saveNotification(InsertNoticeVo notification);
}

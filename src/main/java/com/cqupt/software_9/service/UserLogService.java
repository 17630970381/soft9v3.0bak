package com.cqupt.software_9.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqupt.software_9.entity.UserLog;

import java.util.List;

public interface UserLogService extends IService<UserLog> {
    int insertUserLog(UserLog userLog);



    List<UserLog> getAllLogs();
    void insertLog(String uid, Integer role, String operation);
}

package com.cqupt.software_9.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.software_9.entity.User;
import com.cqupt.software_9.entity.UserLog;
import com.cqupt.software_9.mapper.UserLogMapper;
import com.cqupt.software_9.mapper.UserMapper;
import com.cqupt.software_9.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserLogServiceImpl extends ServiceImpl<UserLogMapper, UserLog>
        implements UserLogService {
    @Autowired
    private UserLogMapper userLogMapper;

    @Resource
    private UserMapper userMapper;
    @Override
    public int insertUserLog(UserLog userLog) {

        int insert = userLogMapper.insert(userLog);
        return insert;
    }

    @Override
    public List<UserLog> getAllLogs() {
        return userLogMapper.getAllLogs();
    }

    @Override
    public void insertLog(String uid, Integer role, String operation) {
        User user = userMapper.selectByUid(uid);

        UserLog logEntity = new UserLog();
        logEntity.setUid(uid);
        logEntity.setUsername(user.getUsername());
        logEntity.setRole(user.getRole());
        logEntity.setOpType(operation);
        // 创建 DateTimeFormatter 对象，定义日期时间的格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 创建 LocalDateTime 对象，存储当前日期和时间
        LocalDateTime now = LocalDateTime.now();
        // 使用 formatter 格式化 LocalDateTime 对象


        userLogMapper.insert(logEntity);
    }
}

package com.cqupt.software_9.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqupt.software_9.common.Result;
import com.cqupt.software_9.entity.AdminDataManage;
import com.cqupt.software_9.entity.TableDescribeEntity;
import com.cqupt.software_9.entity.User;
import com.cqupt.software_9.entity.UserLog;
import com.cqupt.software_9.mapper.AdminDataManageMapper;
import com.cqupt.software_9.service.AdminDataManageService;
import com.cqupt.software_9.service.TableDescribeService;
import com.cqupt.software_9.service.UserLogService;
import com.cqupt.software_9.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


// TODO 公共模块新增类

@RestController
@RequestMapping("/api/sysManage")
public class AdminDataManageController {
    @Autowired
    AdminDataManageService adminDataManageService;

    @Autowired
    private UserLogService userLogService;

    @Autowired
    private UserService userService;

    @Resource
    private AdminDataManageMapper adminDataManageMapper;

    // 文件上传
    @PostMapping("/uploadDataTable")
    public Result uploadDataTable(@RequestParam("file") MultipartFile file,
                             @RequestParam("tableName") String tableName,
                             @RequestParam("userName") String userName,
                             @RequestParam("classPath") String classPath,
                             @RequestParam("uid") String uid,
                             @RequestParam("tableStatus") String tableStatus
        ){
        // 保存表数据信息

        try {
            //  操作日志记录
            UserLog userLog = new UserLog();
            userLog.setUsername(userName);
            // userLog.setId(1);
            userLog.setUid(Integer.parseInt(uid));
            userLog.setOpTime(new Date());
            List<String> featureList = adminDataManageService.uploadDataTable(file, tableName, userName, classPath, uid, tableStatus);

            userLog.setOpType("管理员上传"+tableName+"成功");
            userLogService.save(userLog);

            return Result.success("200",featureList); // 返回表头信息
        }catch (Exception e){
            e.printStackTrace();
            return Result.success(500,"文件上传异常");
        }
    }

    @GetMapping("/selectAdminDataManage")
    public Result<AdminDataManage> selectAdminDataManage(){ // 参数表的Id
        List<AdminDataManage> adminDataManages = adminDataManageService.selectAllDataInfo();
//        System.out.println("数据为："+ JSON.toJSONString(tableDescribeEntity));
        return Result.success("200",adminDataManages);
    }

    @GetMapping("/selectDataByTableName")
    public Result<AdminDataManage> selectDataByTableName(@RequestParam("tableName") String tableName){
        List<AdminDataManage> adminDataManages = adminDataManageService.selectDataByTableName(tableName);
//        System.out.println("数据为："+ JSON.toJSONString(tableDescribeEntity));
        return Result.success("200",adminDataManages);
    }

    @GetMapping("/selectDataByUserName")
    public Result<AdminDataManage> selectDataByUserName(@RequestParam("userName") String userName){
        List<AdminDataManage> adminDataManages = adminDataManageService.selectDataByUserName(userName);
        return Result.success("200",adminDataManages);
    }

    @GetMapping("/selectDataByDiseaseName")
    public Result<AdminDataManage> selectDataByDiseaseName(@RequestParam("diseaseName") String diseaseName){
        List<AdminDataManage> adminDataManages = adminDataManageService.selectDataByDiseaseName(diseaseName);
        return Result.success("200",adminDataManages);
    }

    @GetMapping("/updateById")
    public Result<AdminDataManage> updateById(
            @RequestParam("id") String id,
            @RequestParam("tableName") String tableName,
            @RequestParam("tableStatus") String tableStatus,
            @RequestParam("uid") String uid
            ){
//        adminDataManageService.updateById(id, tableName, tableStatus);
//        return Result.success("200","已经更改到数据库");
        //  操作日志记录


        try {
            UserLog userLog = new UserLog();
            User user = new User();
            user = userService.getById(Integer.parseInt(uid));
            userLog.setUsername(user.getUsername());
            // userLog.setId(1);
            userLog.setUid(Integer.parseInt(uid));
            userLog.setOpTime(new Date());

            // 假设 adminDataManageService 是一个服务层组件，并且 updateById 方法执行更新操作
            // 这里假设 updateById 方法返回一个布尔值，表示更新是否成功
            boolean updateResult = adminDataManageService.updateById(id, tableName, tableStatus);

            if (updateResult) {
                userLog.setOpType("管理员修改表"+tableName+"信息成功");

                userLogService.save(userLog);
                // 更新成功，返回成功结果，这里假设 AdminDataManage 是更新后的数据对象
                AdminDataManage updatedData = adminDataManageMapper.selectById(id); // 假设有一个方法可以获取更新后的数据

                return Result.success(200, "更新成功", updatedData);
            } else {
                // 更新失败，返回失败结果
                return Result.success("400", "更新失败");
            }
        } catch (Exception e) {
            // 处理可能出现的任何异常，例如数据库连接失败等
            // 记录异常信息，根据实际情况决定是否需要发送错误日志
            // 这里返回一个通用的错误信息
            return Result.success("500", "更新失败，发生未知错误");
        }
    }

    @GetMapping("/deleteByTableName")
    public Result<AdminDataManage> deleteByTableName(
            @RequestParam("tableName") String tableName,
            @RequestParam("uid") String uid
    ){
//        adminDataManageService.deleteByTableName(tableName);
//        return Result.success("200","已在数据库中删除了"+tableName+"表");

        try {
            // 假设 adminDataManageService 是一个服务层组件，并且 deleteByTableName 方法执行删除操作
            // 这里假设 deleteByTableName 方法返回一个布尔值，表示删除是否成功
            boolean deleteResult = adminDataManageService.deleteByTableName(tableName);

            //  操作日志记录

            UserLog userLog = new UserLog();
            User user = new User();
            user = userService.getById(Integer.parseInt(uid));
            userLog.setUsername(user.getUsername());
            // userLog.setId(1);
            userLog.setUid(Integer.parseInt(uid));
            userLog.setOpTime(new Date());

            if (deleteResult) {
                userLog.setOpType("管理员删除表"+tableName+"成功");
                userLogService.save(userLog);
                // 删除成功，返回成功结果
                return Result.success(200, "已在数据库中删除了表: " + tableName,adminDataManageMapper.selectAllDataInfo());
            } else {
                // 删除失败，返回失败结果
                return Result.success("400", "删除失败，表: " + tableName + " 不存在或删除操作未执行");
            }
        } catch (Exception e) {
            // 处理可能出现的任何异常，例如数据库连接失败等
            // 记录异常信息，根据实际情况决定是否需要发送错误日志
            // 这里返回一个通用的错误信息
            return Result.success("500", "删除失败，发生未知错误");
        }
    }
}

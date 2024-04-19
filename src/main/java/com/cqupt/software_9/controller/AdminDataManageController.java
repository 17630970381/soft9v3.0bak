package com.cqupt.software_9.controller;



import com.cqupt.software_9.common.Result;
import com.cqupt.software_9.entity.AdminDataManage;
import com.cqupt.software_9.entity.CategoryEntity;
import com.cqupt.software_9.service.*;
import com.cqupt.software_9.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO 因为操作该模块的用户肯定是管理员，因此在插入日志时将role角色固定为0， 管理员状态

@RestController
@RequestMapping("/api/sysManage")
public class AdminDataManageController {
    @Autowired
    private AdminDataManageService adminDataManageService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    private UserLogService logService;
    // 文件上传
    @PostMapping("/uploadDataTable")
    public Result uploadDataTable(@RequestParam("file") MultipartFile file,
//                             @RequestParam("tableId") String tableId,
                             @RequestParam("pid") String pid,
                             @RequestParam("tableName") String tableName,
                             @RequestParam("userName") String userName,
                             @RequestParam("classPath") String classPath,
                             @RequestParam("uid") String uid,   // 传表中涉及到的用户的uid
                             @RequestParam("tableStatus") String tableStatus,
                             @RequestParam("tableSize") float tableSize,
                             @RequestParam("current_uid") String current_uid //操作用户的uid
        ){
        // 保存表数据信息
        try {
//            String tableId="";
            List<String> featureList = adminDataManageService.uploadDataTable(file, pid, tableName, userName, classPath, uid, tableStatus, tableSize, current_uid);
            return Result.success("200",featureList); // 返回表头信息
        }catch (Exception e){
            e.printStackTrace();
            logService.insertLog(current_uid, 0, e.getMessage());
            return Result.success(500,"文件上传异常");
        }
    }

    @GetMapping("/selectAdminDataManage")
    public Result<AdminDataManage> selectAdminDataManage(
//            @RequestParam("current_uid") String current_uid
    ){ // 参数表的Id
        List<AdminDataManage> adminDataManages = adminDataManageService.selectAllDataInfo();
//        System.out.println("数据为："+ JSON.toJSONString(tableDescribeEntity));
        Map<String, Object> ret =  new HashMap<>();
        ret.put("list", adminDataManages);
        ret.put("total", adminDataManages.size());

        return Result.success("200",ret);
//        return Result.success("200",adminDataManages);
    }

    @GetMapping("/selectDataByName")
    public Result<AdminDataManage> selectDataByName(
            @RequestParam("searchType") String searchType,
            @RequestParam("name") String name
//            @RequestParam("current_uid") String current_uid
    ){
        List<AdminDataManage> adminDataManages = adminDataManageService.selectDataByName(searchType, name);
//        System.out.println("数据为："+ JSON.toJSONString(tableDescribeEntity));
        Map<String, Object> ret =  new HashMap<>();
        ret.put("list", adminDataManages);
        ret.put("total", adminDataManages.size());

        return Result.success("200",ret);
    }

    @GetMapping("/selectDataById")
    public Result<AdminDataManage> selectDataById(
            @RequestParam("id") String id
//            @RequestParam("current_uid") String current_uid
    ){
        AdminDataManage adminDataManage = adminDataManageService.selectDataById(id);
//        System.out.println("数据为："+ JSON.toJSONString(tableDescribeEntity));

        return Result.success("200",adminDataManage);
    }

    @GetMapping("/updateAdminDataManage")
    public Result<AdminDataManage> updateAdminDataManage(
            @RequestParam("id") String id,
            @RequestParam("tableid") String tableid,
            @RequestParam("oldTableName") String oldTableName,
            @RequestParam("tableName") String tableName,
            @RequestParam("tableStatus") String tableStatus,
            @RequestParam("current_uid") String current_uid
            ){
        adminDataManageService.updateInfo(id, tableid, oldTableName, tableName, tableStatus, current_uid);

        return Result.success("200","已经更改到数据库");
    }

    @GetMapping("/getLevel2Label")
    public Result<List<CategoryEntity>> getLevel2Label(
//            @RequestParam("current_uid") String current_uid
    ){
        List<CategoryEntity> res = categoryService.getLevel2Label();
        return Result.success("200",res);
    }
    @GetMapping("/getLabelByPid")
    public Result<List<CategoryEntity>> getLabelsByPid(
            @RequestParam("pid") String pid
//            @RequestParam("current_uid") String current_uid
    ){
        List<CategoryEntity> res = categoryService.getLabelsByPid(pid);
        return Result.success("200",res);
    }

    @GetMapping("/deleteByTableName")
    public Result<AdminDataManage> deleteByTableName(
            @RequestParam("id") String id,
            @RequestParam("uid") String uid,
            @RequestParam("tableSize") float tableSize,
            @RequestParam("tableId") String tableId,
            @RequestParam("tableName") String tableName,
            @RequestParam("current_uid") String current_uid
    ){
//        System.out.println();

        adminDataManageService.deleteByTableName(tableName);// 【因为数据库中表名是不能重名的】
        logService.insertLog(current_uid, 0, "删除了public模式下储存的表:" + tableName);
        adminDataManageService.deleteByTableId(tableId);// 在table_describe中删除记录
        logService.insertLog(current_uid, 0, "删除了table_describe表中的"+tableName+"记录信息");
        categoryService.removeNode(tableId);// 在category中设置is_delete为1
        logService.insertLog(current_uid, 0, "在category中设置is_delete为1" );

//        float tableSize = adminDataManage.getTableSize();
        userService.addTableSize(uid, tableSize);
        logService.insertLog(current_uid, 0, "在user表中修改容量" );
        return Result.success("200","已在数据库中删除了"+tableName+"表");
    }
}
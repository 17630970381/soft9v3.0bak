package com.cqupt.software_9.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqupt.software_9.common.Result;
import com.cqupt.software_9.entity.AdminDataManage;
import com.cqupt.software_9.entity.CategoryEntity;
import com.cqupt.software_9.entity.DataManager;
import com.cqupt.software_9.mapper.CategoryMapper;
import com.cqupt.software_9.mapper.DataManagerMapper;
import com.cqupt.software_9.service.AdminDataManageService;
import com.cqupt.software_9.service.CategoryService;
import com.cqupt.software_9.service.UserLogService;
import com.cqupt.software_9.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

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

    @Autowired
    private CategoryMapper categoryMapper;

    @Resource
    private DataManagerMapper dataManagerMapper;
    // 文件上传
    // 文件上传
    @PostMapping("/uploadDataTable")
    public Result uploadDataTable(@RequestParam("file") MultipartFile file,
//                             @RequestParam("tableId") String tableId,
                                  @RequestParam("pid") String pid,
                                  @RequestParam("tableName") String tableName,
                                  @RequestParam("userName") String userName,
                                  @RequestParam("ids") String[] ids,
                                  @RequestParam("uid") String uid,   // 传表中涉及到的用户的uid
                                  @RequestParam("tableStatus") String tableStatus,
                                  @RequestParam("tableSize") float tableSize,
                                  @RequestParam("current_uid") String current_uid //操作用户的uid
    ){
        // 保存表数据信息
        try {
//            String tableId="";
            // 管理员端-数据管理新更改
//            传入的是category的id集合，根据id获取labels拼接成classpath
            String classPath = "公共数据集";
            for (String id : ids){
                CategoryEntity categoryEntity = categoryMapper.selectById(id);
                classPath += "/" + categoryEntity.getLabel();
            }
            classPath += "/" + tableName;
            List<String> featureList = adminDataManageService.uploadDataTable(file, pid, tableName, userName, classPath, uid, tableStatus, tableSize, current_uid);

            return Result.success("200",featureList); // 返回表头信息
        }catch (Exception e){
            e.printStackTrace();
            logService.insertLog(current_uid, 0, e.getMessage());
            return Result.success(500,"文件上传异常");
        }
    }


    // 管理员端-数据管理新更改
    @GetMapping("/selectDataDiseases")
    public Result<AdminDataManage> selectDataDiseases(
//            @RequestParam("current_uid") String current_uid
    ) { // 参数表的Id
        List<CategoryEntity> res = categoryService.getLevel2Label();

        List<Object> retList = new ArrayList<>();
        for (CategoryEntity category : res) {
            Map<String, Object> ret = new HashMap<>();
            ret.put("label", category.getLabel());
            ret.put("value", category.getId());
            if (selectCategoryDataDiseases(category.getId()).size() > 0) {
                ret.put("children", selectCategoryDataDiseases(category.getId()));
            }

            retList.add(ret);
        }
        System.out.println(retList);


        return Result.success("200", retList);
    }
    // 管理员端-数据管理新更改
    public List<Map<String, Object>> selectCategoryDataDiseases(String pid){
        List<Map<String, Object>> retList = new ArrayList<>();
        List<CategoryEntity> res = categoryService.getLabelsByPid(pid);
        for (CategoryEntity category : res) {
            Map<String, Object> ret =  new HashMap<>();
            ret.put("label", category.getLabel());
            ret.put("value", category.getId());
            if (selectCategoryDataDiseases(category.getId()).size() > 0) {
                ret.put("children", selectCategoryDataDiseases(category.getId()));
            }
            retList.add(ret);
        }
        return retList;
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
    ){
        AdminDataManage adminDataManage = adminDataManageService.selectDataById(id); // 根据id获取table_describe表的那一行
        Map<String, Object> ret =  new HashMap<>();
        ret.put("object", adminDataManage);

        CategoryEntity categoryEntity = categoryMapper.selectById(adminDataManage.getTableId());// 根据id获取table_describe表的table_id与category形成映射
        List<String> pids = new ArrayList<>();
        while (!categoryEntity.getParentId().equals("1")){ // 筛选除疾病列表结点的下面结点
            categoryEntity = categoryMapper.selectById(categoryEntity.getParentId());
            pids.add(categoryEntity.getId()); // 迭代添加父节点id
        }
        Collections.reverse(pids); // 反转，使得父节点id在前面

        ret.put("ids", pids); // 包含疾病结点的id，不包含表id
        return Result.success("200",ret);
    }
    @PostMapping("/updateAdminDataManage")
    public Result<AdminDataManage> updateAdminDataManage(
            @RequestParam("id") String id,
            @RequestParam("tableid") String tableid,
            @RequestParam("oldTableName") String oldTableName,
            @RequestParam("tableName") String tableName,
            @RequestParam("tableStatus") String tableStatus,
            @RequestParam("pids") String[] pids,  // 父节点id列表
            @RequestParam("current_uid") String current_uid
    ){
        adminDataManageService.updateInfo(id, tableid, oldTableName, tableName, tableStatus, pids, current_uid);
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
        QueryWrapper<DataManager> wrapper = new QueryWrapper<>();
        wrapper.eq("tablename",tableName);
        dataManagerMapper.delete(wrapper);// 在data_manager中删除记录
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

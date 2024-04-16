package com.cqupt.software_9.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqupt.software_9.entity.AdminDataManage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

// TODO 公共模块新增类
public interface AdminDataManageService extends IService<AdminDataManage> {
    List<String>  uploadDataTable(MultipartFile file, String tableName, String userName, String classPath, String uid, String tableStatus) throws IOException, ParseException;

    List<AdminDataManage> selectAllDataInfo();

    //根据表名搜索
    List<AdminDataManage> selectDataByTableName(String tableName);
    //根据用户名搜索
    List<AdminDataManage> selectDataByUserName(String userName);
    //根据疾病模糊搜索
    List<AdminDataManage> selectDataByDiseaseName(String diseaseName);

    boolean deleteByTableName(String tablename);

    boolean updateById(String id, String tableName, String tableStatus);
}

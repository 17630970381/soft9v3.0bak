package com.cqupt.software_9.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.cqupt.software_9.entity.AdminDataManage;
import com.cqupt.software_9.entity.AdminDataManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Mapper
public interface AdminDataManageMapper extends BaseMapper<AdminDataManage> {

    void createTable(@Param("headers") String[] headers, @Param("tableName") String tableName);
    void insertRow(@Param("row") String[] row, @Param("tableName") String tableName);
    List<String>  uploadDataTable(MultipartFile file, String tableName, String userName, String classPath, int uid, String tableStatus) throws IOException, ParseException;

    List<AdminDataManage> selectAllDataInfo();

    //根据表名搜索
    List<AdminDataManage> selectDataByTableName(String tableName);
    //根据用户名搜索
    List<AdminDataManage> selectDataByUserName(String userName);
    //根据疾病模糊搜索
    List<AdminDataManage> selectDataByDiseaseName(String diseaseName);

    void updateById(String id, String tableName, String tableStatus);

    void deleteByTableName(String tablename);
}

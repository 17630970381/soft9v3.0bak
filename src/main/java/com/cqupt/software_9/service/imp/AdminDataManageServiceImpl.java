package com.cqupt.software_9.service.imp;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.cqupt.software_9.entity.AdminDataManage;
import com.cqupt.software_9.mapper.AdminDataManageMapper;
import com.cqupt.software_9.service.AdminDataManageService;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* @author hp
* @description 针对表【t_table_manager】的数据库操作Service实现
* @createDate 2023-05-23 15:10:20
*/
@Service
public class AdminDataManageServiceImpl extends ServiceImpl<AdminDataManageMapper, AdminDataManage>
    implements AdminDataManageService {
    @Autowired
    private AdminDataManageMapper adminDataManageMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public List<String> storeTableData(MultipartFile file, String tableName) throws IOException {
        ArrayList<String> featureList = null;
        if (!file.isEmpty()) {
            // 使用 OpenCSV 解析 CSV 文件
            Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8"));
            CSVReader csvReader = new CSVReader(reader);
            List<String[]> csvData = csvReader.readAll();
            csvReader.close();
            // 获取表头信息
            String[] headers = csvData.get(0);
            featureList = new ArrayList<String>(Arrays.asList(headers));
            System.out.println("表头信息为："+ JSON.toJSONString(headers));
            // 删除表头行，剩余的即为数据行
            csvData.remove(0);
            // 创建表信息
            adminDataManageMapper.createTable(headers,tableName);
            // 保存表头信息和表数据到数据库中
            for (String[] row : csvData) { // 以此保存每行信息到数据库中
                adminDataManageMapper.insertRow(row,tableName);
            }
        }
        return featureList;
    }


    @Override
    public List<String> uploadDataTable(MultipartFile file, String tableName, String userName, String classPath, String uid, String tableStatus) throws IOException, ParseException {
        // 封住表描述信息
        AdminDataManage adminDataManageEntity = new AdminDataManage();

        adminDataManageEntity.setTableName(tableName);
        adminDataManageEntity.setCreateUser(userName);
        // 解析系统当前时间
        adminDataManageEntity.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        adminDataManageEntity.setClassPath(classPath);
        adminDataManageEntity.setTableStatus(tableStatus);
        adminDataManageMapper.insert(adminDataManageEntity);

        List<String> featureList = storeTableData(file, tableName);
        // 保存数据库
        System.out.println("表描述信息插入成功, 动态建表成功");
        return featureList;
    }

    @Override
    public List<AdminDataManage> selectAllDataInfo() {
        return adminDataManageMapper.selectAllDataInfo();
    }

    @Override
    public List<AdminDataManage> selectDataByTableName(String tableName) {
        return adminDataManageMapper.selectDataByTableName(tableName);
    }

    @Override
    public List<AdminDataManage> selectDataByUserName(String userName) {
        return adminDataManageMapper.selectDataByUserName(userName);
    }

    @Override
    public List<AdminDataManage> selectDataByDiseaseName(String diseaseName) {
        return adminDataManageMapper.selectDataByDiseaseName(diseaseName);
    }

    @Override
    public boolean deleteByTableName(String tableName) {
        adminDataManageMapper.deleteByTableName(tableName);
        return false;
    }

    @Override
    public boolean updateById(String id, String tableName, String tableStatus) {
        adminDataManageMapper.updateById(id, tableName, tableStatus);
        return false;
    }


}





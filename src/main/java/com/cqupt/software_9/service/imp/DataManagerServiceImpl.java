package com.cqupt.software_9.service.imp;


import com.cqupt.software_9.mapper.DataManagerMapper;
import com.cqupt.software_9.entity.DataManager;
import com.cqupt.software_9.service.Adapter.DataManagerServiceAdapter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataManagerServiceImpl extends DataManagerServiceAdapter {

    @Autowired
    private DataManagerMapper dataManagerMapper;

    @Override
    public List<DataManager> getDatawithoutresult() {
        return dataManagerMapper.getDataManagerwithoutresult();
    }

    public PageInfo<DataManager> getDatawithoutresult(int page,int pageSize){
        PageHelper.startPage(page,pageSize);
        List<DataManager> data=dataManagerMapper.getDataManagerwithoutresult();
        return new PageInfo<>(data);
    }

    @Override
    public List<DataManager> getDetail(String diseasename) {
        return dataManagerMapper.getDetail(diseasename);
    }

    @Override
    public String getTableNameByID(Integer id) {
        return dataManagerMapper.getTableNameByID(id);
    }

    @Override
    public List<Map<String, String>> getInfoByTableName(String tableName) {
        return dataManagerMapper.getInfoByTableName(tableName);
    }



}

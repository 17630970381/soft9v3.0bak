package com.cqupt.software_9.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.entity.DataManager;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataManagerMapper extends BaseMapper<DataManager> {

    List<DataManager>  getDataManagerwithoutresult();
    void updata(String tableName);
    void insertDataManager(DataManager dataManager);

    List<DataManager> getDetail(String diseasename);

    String getTableNameByID(Integer id);

    List<Map<String, String>> getInfoByTableName(String tableName);

    List<DataManager> getTableNameByUiD(Integer uid);

    DataManager getdetailBytableName(String tablename);

    List<String> getDiseaseName();

    List<String> getTableByDisease(String diseasename);

    List<DataManager> getTableInfo(String tableName);

    Integer getRow(String tablename);

    Integer getColumn(String tablename);
}

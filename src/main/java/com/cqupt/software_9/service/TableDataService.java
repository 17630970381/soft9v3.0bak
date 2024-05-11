package com.cqupt.software_9.service;

import com.cqupt.software_9.entity.CategoryEntity;
import com.cqupt.software_9.vo.CreateTableFeatureVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface TableDataService {
    List<LinkedHashMap<String,Object>> getTableData(String TableId, String tableName);

    List<String> uploadFile(MultipartFile file, String tableName, String type, String user, String userId, String parentId, String parentType, String status, double size, String is_upload, String is_filter) throws IOException, ParseException;



    List<LinkedHashMap<String, Object>> getFilterDataByConditions(List<CreateTableFeatureVo> characterList,CategoryEntity nodeData);
    List<LinkedHashMap<String, Object>> getFilterDataByConditionsCreateTable(List<CreateTableFeatureVo> characterList,CategoryEntity nodeData);


    List<String> ParseFileCol(MultipartFile file, String tableName) throws IOException;

    List<Map<String, Object>> getInfoByTableName(String tableName);

    void createTable(String dataName, List<CreateTableFeatureVo> characterList, String createUser, CategoryEntity nodeData, String uid, String username, String isFilter, String isUpload);


    boolean checkRepeat(String tablename);
    void createFilterBtnTable(String tableName, List<CreateTableFeatureVo> characterList, String createUser,String status,String uid,String username,String IsFilter,String IsUpload,String uid_list,String nodeid);
    List<LinkedHashMap<String, Object>> getFilterDataByConditionsByDieaseId(List<CreateTableFeatureVo> characterList,String uid,String username,String nodeid);
}

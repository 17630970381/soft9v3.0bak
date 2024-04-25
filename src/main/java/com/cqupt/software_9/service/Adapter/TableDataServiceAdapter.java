package com.cqupt.software_9.service.Adapter;

import com.cqupt.software_9.entity.CategoryEntity;
import com.cqupt.software_9.service.TableDataService;
import com.cqupt.software_9.vo.CreateTableFeatureVo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;

public abstract class TableDataServiceAdapter implements TableDataService {

    @Override
    public List<LinkedHashMap<String, Object>> getTableData(String TableId, String tableName) {
        return null;
    }



    @Transactional(propagation = Propagation.REQUIRED) // 事务控制
    public abstract List<String> uploadFile(MultipartFile file, String tableName, String type, String user, String userId, String parentId, String parentType, String status, Double size, String is_upload, String is_filter) throws IOException, ParseException;



    @Override
    public List<LinkedHashMap<String, Object>> getFilterDataByConditions(List<CreateTableFeatureVo> characterList, CategoryEntity nodeData) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public abstract void createTable(String tableName, List<CreateTableFeatureVo> characterList, String createUser, CategoryEntity nodeData, String uid, String username, String IsFilter, String IsUpload);

    @Override
    public List<String> ParseFileCol(MultipartFile file, String tableName) throws IOException {
        return null;
    }


}

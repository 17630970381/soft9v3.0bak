package com.cqupt.software_9.service.Adapter;

import com.cqupt.software_9.entity.CategoryEntity;
import com.cqupt.software_9.service.TableDataService;
import com.cqupt.software_9.vo.CreateTableFeatureVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TableDataServiceAdapter implements TableDataService {

    @Override
    public List<LinkedHashMap<String, Object>> getTableData(String TableId, String tableName) {
        return null;
    }

    @Override
    public List<String> uploadFile(MultipartFile file, String tableName, String type, String user, int userId, String parentId, String parentType) throws IOException, ParseException {
        return null;
    }

    @Override
    public void createTable(String tableName, List<CreateTableFeatureVo> characterList, String createUser, CategoryEntity nodeData) {

    }

    @Override
    public List<LinkedHashMap<String, Object>> getFilterDataByConditions(List<CreateTableFeatureVo> characterList, CategoryEntity nodeData) {
        return null;
    }


}

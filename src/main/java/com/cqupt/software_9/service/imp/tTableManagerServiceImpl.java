package com.cqupt.software_9.service.imp;

import com.cqupt.software_9.common.TableManagerDTO;
import com.cqupt.software_9.mapper.tTableManagerMapper;
import com.cqupt.software_9.entity.tTableField;
import com.cqupt.software_9.service.Adapter.tTableManagerServiceAdapter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class tTableManagerServiceImpl extends tTableManagerServiceAdapter {

    @Resource
    private tTableManagerMapper tTableManagerMapper;;

    @Override
    public void insertTableManager(TableManagerDTO tableManagerDTO) {
        tTableManagerMapper.insertTableManager(tableManagerDTO);
    }

    @Override
    public void deletebyname(String tableName) {
        tTableManagerMapper.deletebyname(tableName);
    }

    @Override
    public List<String> getTableNames(Integer uid) {
        List<String> tableNames = tTableManagerMapper.getTableNames(uid);
        return tableNames;
    }

    @Override
    public List<tTableField> getFiledNamesByTableName(String table_name){
        List<tTableField> fields = tTableManagerMapper.getFiledNamesByTableName(table_name);
        return fields;
    }

    @Override
    public boolean updateFieldValues(String table_name, tTableField field) {
        String fieldName = field.getField_name();
        String demography = field.getIs_demography();
        String physiological = field.getIs_physiological();
        String sociology = field.getIs_sociology();
        String label = field.getIs_label();
        tTableManagerMapper.updateFieldValues(table_name, fieldName,demography,physiological,sociology,label);

        return true;
    }

    @Override
    public List<String> getFiledSByTableName(String name) {
        return tTableManagerMapper.getFiledsByTableName(name);
    }
}

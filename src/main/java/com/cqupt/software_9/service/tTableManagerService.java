package com.cqupt.software_9.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqupt.software_9.common.TableManagerDTO;
import com.cqupt.software_9.entity.tTableField;
import com.cqupt.software_9.entity.tTableManager;

import java.util.List;


public interface tTableManagerService extends IService<tTableManager> {
    List<String> getTableNames(Integer uid);
    List<tTableField> getFiledNamesByTableName(String table_name);
    boolean updateFieldValues(String table_name, tTableField field);
    void insertTableManager(TableManagerDTO tableManagerDTO);
    void deletebyname(String tablename);

    List<String> getFiledSByTableName(String name);
}

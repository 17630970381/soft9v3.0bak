package com.cqupt.software_9.service.Adapter;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.common.TableManagerDTO;
import com.cqupt.software_9.entity.tTableField;
import com.cqupt.software_9.entity.tTableManager;
import com.cqupt.software_9.service.tTableManagerService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class tTableManagerServiceAdapter implements tTableManagerService {
    @Override
    public List<String> getTableNames(Integer uid) {
        return null;
    }

    @Override
    public List<tTableField> getFiledNamesByTableName(String table_name) {
        return null;
    }

    @Override
    public boolean updateFieldValues(String table_name, tTableField field) {
        return false;
    }

    @Override
    public void insertTableManager(TableManagerDTO tableManagerDTO) {

    }

    @Override
    public void deletebyname(String tablename) {

    }

    @Override
    public List<String> getFiledSByTableName(String name) {
        return null;
    }

    @Override
    public boolean saveBatch(Collection<tTableManager> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<tTableManager> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<tTableManager> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(tTableManager entity) {
        return false;
    }

    @Override
    public tTableManager getOne(Wrapper<tTableManager> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<tTableManager> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<tTableManager> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<tTableManager> getBaseMapper() {
        return null;
    }

    @Override
    public Class<tTableManager> getEntityClass() {
        return null;
    }
}

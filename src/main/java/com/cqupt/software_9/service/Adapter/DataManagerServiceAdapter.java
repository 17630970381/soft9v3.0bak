package com.cqupt.software_9.service.Adapter;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.entity.DataManager;
import com.cqupt.software_9.mapper.DataManagerMapper;
import com.cqupt.software_9.service.DataManagerService;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DataManagerServiceAdapter implements DataManagerService {

    @Resource
    private DataManagerMapper dataManagerMapper;
    @Override
    public List<DataManager> getDatawithoutresult() {
        return null;
    }

    @Override
    public boolean saveBatch(Collection<DataManager> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<DataManager> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<DataManager> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(DataManager entity) {
        return false;
    }

    @Override
    public DataManager getOne(Wrapper<DataManager> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<DataManager> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<DataManager> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<DataManager> getBaseMapper() {
        return null;
    }

    @Override
    public Class<DataManager> getEntityClass() {
        return null;
    }

    @Override
    public PageInfo<DataManager> getDatawithoutresult(int page, int pageSize) {
        return null;
    }

    @Override
    public List<DataManager> getDetail(String diseasename) {
        return null;
    }

    @Override
    public String getTableNameByID(Integer id) {
        return null;
    }

    @Override
    public List<Map<String, String>> getInfoByTableName(String tableName) {
        return null;
    }

    @Override
    public String getStartAndEndTime() {
        return null;
    }


}

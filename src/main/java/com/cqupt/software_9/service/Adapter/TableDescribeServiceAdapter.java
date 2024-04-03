package com.cqupt.software_9.service.Adapter;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.entity.TableDescribeEntity;
import com.cqupt.software_9.service.TableDescribeService;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public class TableDescribeServiceAdapter implements TableDescribeService {
    @Override
    public boolean saveBatch(Collection<TableDescribeEntity> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<TableDescribeEntity> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<TableDescribeEntity> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(TableDescribeEntity entity) {
        return false;
    }

    @Override
    public TableDescribeEntity getOne(Wrapper<TableDescribeEntity> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<TableDescribeEntity> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<TableDescribeEntity> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<TableDescribeEntity> getBaseMapper() {
        return null;
    }

    @Override
    public Class<TableDescribeEntity> getEntityClass() {
        return null;
    }
}

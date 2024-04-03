package com.cqupt.software_9.service.Adapter;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.entity.FieldManagementEntity;
import com.cqupt.software_9.service.FieldManagementService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class FieldManagementServiceAdapter implements FieldManagementService {

    @Override
    public List<FieldManagementEntity> getFiledByDiseaseName(String diseaseName) {
        return null;
    }

    @Override
    public void updateFieldsByDiseaseName(String diseaseName, List<String> fields) {

    }

    @Override
    public boolean saveBatch(Collection<FieldManagementEntity> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<FieldManagementEntity> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<FieldManagementEntity> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(FieldManagementEntity entity) {
        return false;
    }

    @Override
    public FieldManagementEntity getOne(Wrapper<FieldManagementEntity> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<FieldManagementEntity> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<FieldManagementEntity> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<FieldManagementEntity> getBaseMapper() {
        return null;
    }

    @Override
    public Class<FieldManagementEntity> getEntityClass() {
        return null;
    }
}

package com.cqupt.software_9.service.Adapter;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.entity.CategoryEntity;
import com.cqupt.software_9.service.CategoryService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CategoryServiceAdapter implements CategoryService {


    @Override
    public List<CategoryEntity> getCategory() {
        return null;
    }

    @Override
    public void removeNode(String id) {

    }

    @Override
    public void addParentDisease(String diseaseName) {

    }

    @Override
    public boolean saveBatch(Collection<CategoryEntity> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<CategoryEntity> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<CategoryEntity> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(CategoryEntity entity) {
        return false;
    }

    @Override
    public CategoryEntity getOne(Wrapper<CategoryEntity> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<CategoryEntity> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<CategoryEntity> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<CategoryEntity> getBaseMapper() {
        return null;
    }

    @Override
    public Class<CategoryEntity> getEntityClass() {
        return null;
    }
}

package com.cqupt.software_9.service.Adapter;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.entity.Category2Entity;
import com.cqupt.software_9.service.Category2Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Category2ServiceAdapter implements Category2Service {

    @Override
    public List<Category2Entity> getCategory() {
        return null;
    }

    @Override
    public List<Category2Entity> getCategory2() {
        return null;
    }

    @Override
    public void removeNode(String id) {

    }

    @Override
    public boolean saveBatch(Collection<Category2Entity> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<Category2Entity> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<Category2Entity> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(Category2Entity entity) {
        return false;
    }

    @Override
    public Category2Entity getOne(Wrapper<Category2Entity> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<Category2Entity> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<Category2Entity> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<Category2Entity> getBaseMapper() {
        return null;
    }

    @Override
    public Class<Category2Entity> getEntityClass() {
        return null;
    }
}

package com.cqupt.software_9.service.Adapter;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.entity.Nodes;
import com.cqupt.software_9.entity.Relationships;
import com.cqupt.software_9.service.NodesService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class NodesServiceAdapter implements NodesService {
    @Override
    public List<Nodes> getAllNodes() {
        return null;
    }

    @Override
    public List<Relationships> getRelationships() {
        return null;
    }

    @Override
    public boolean saveBatch(Collection<Nodes> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<Nodes> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<Nodes> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(Nodes entity) {
        return false;
    }

    @Override
    public Nodes getOne(Wrapper<Nodes> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<Nodes> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<Nodes> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<Nodes> getBaseMapper() {
        return null;
    }

    @Override
    public Class<Nodes> getEntityClass() {
        return null;
    }
}

package com.cqupt.software_9.service.Adapter;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.common.Result;
import com.cqupt.software_9.entity.StasticOne;
import com.cqupt.software_9.service.StasticOneService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class StasticOneServiceAdapter implements StasticOneService {
    @Override
    public StasticOne getStasticOne() {
        return null;
    }

    @Override
    public Result getDiseases() {
        return null;
    }

    @Override
    public List<String> getAllTableNames() {
        return null;
    }

    @Override
    public List<String> getAllUserBuiltTableNames() {
        return null;
    }

    @Override
    public String getType(String tablename) {
        return null;
    }

    @Override
    public Integer getPosNumber(String tablename, String type) {
        return null;
    }

    @Override
    public Integer getNegNumber(String tablename, String type) {
        return null;
    }

    @Override
    public boolean saveBatch(Collection<StasticOne> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<StasticOne> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<StasticOne> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(StasticOne entity) {
        return false;
    }

    @Override
    public StasticOne getOne(Wrapper<StasticOne> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<StasticOne> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<StasticOne> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<StasticOne> getBaseMapper() {
        return null;
    }

    @Override
    public Class<StasticOne> getEntityClass() {
        return null;
    }
}

package com.cqupt.software_9.service.Adapter;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.common.MergeData;
import com.cqupt.software_9.service.MergeDataService;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public class MergeDataServiceAdapter implements MergeDataService {

    @Override
    public Map<String, Map<Integer, Long>> countPatientsByAgeRangeForAllDiagnoses() {
        return null;
    }

    @Override
    public Map<String, Integer> getDiseaseRates() {
        return null;
    }

    @Override
    public boolean saveBatch(Collection<MergeData> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<MergeData> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<MergeData> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(MergeData entity) {
        return false;
    }

    @Override
    public MergeData getOne(Wrapper<MergeData> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<MergeData> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<MergeData> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<MergeData> getBaseMapper() {
        return null;
    }

    @Override
    public Class<MergeData> getEntityClass() {
        return null;
    }
}

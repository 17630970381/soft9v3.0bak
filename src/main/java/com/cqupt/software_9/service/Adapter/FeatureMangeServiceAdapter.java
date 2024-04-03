package com.cqupt.software_9.service.Adapter;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.entity.FeatureEntity;
import com.cqupt.software_9.service.FeatureManageService;
import com.cqupt.software_9.vo.FeatureListVo;
import com.cqupt.software_9.vo.FeatureVo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class FeatureMangeServiceAdapter implements FeatureManageService {

    @Override
    public List<FeatureVo> getFeatureList(String belongType) {
        return null;
    }

    @Override
    public void insertFeatures(FeatureListVo featureListVo) {

    }

    @Override
    public boolean saveBatch(Collection<FeatureEntity> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<FeatureEntity> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<FeatureEntity> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(FeatureEntity entity) {
        return false;
    }

    @Override
    public FeatureEntity getOne(Wrapper<FeatureEntity> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<FeatureEntity> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<FeatureEntity> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<FeatureEntity> getBaseMapper() {
        return null;
    }

    @Override
    public Class<FeatureEntity> getEntityClass() {
        return null;
    }
}

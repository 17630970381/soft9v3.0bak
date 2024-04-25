package com.cqupt.software_9.service.Adapter;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.common.Result;
import com.cqupt.software_9.entity.CategoryEntity;
import com.cqupt.software_9.service.CategoryService;
import com.cqupt.software_9.vo.AddDiseaseVo;
import com.cqupt.software_9.vo.UpdateDiseaseVo;

import java.util.Collection;
import java.util.Collections;
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
    public void removeNode(String id, String label) {

    }

    @Override
    public void addParentDisease(String diseaseName) {

    }

    @Override
    public List<CategoryEntity> getAllDisease() {
        return Collections.emptyList();
    }

    @Override
    public Integer addCategory(AddDiseaseVo addDiseaseVo) {
        return null;
    }

    @Override
    public Result updateCategory(UpdateDiseaseVo updateDiseaseVo) {
        return null;
    }

    @Override
    public void removeCategorys(List<String> deleteIds) {

    }

    @Override
    public List<CategoryEntity> getLevel2Label() {
        return null;
    }

    @Override
    public List<CategoryEntity> getLabelsByPid(String pid) {
        return null;
    }

    @Override
    public List<CategoryEntity> getCategory(String uid) {
        return null;
    }

    @Override
    public void changeStatus(CategoryEntity categoryEntity) {

    }

    @Override
    public List<CategoryEntity> getTaskCategory() {
        return null;
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

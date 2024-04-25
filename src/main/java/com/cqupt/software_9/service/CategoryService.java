package com.cqupt.software_9.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqupt.software_9.common.Result;
import com.cqupt.software_9.entity.CategoryEntity;
import com.cqupt.software_9.vo.AddDiseaseVo;
import com.cqupt.software_9.vo.UpdateDiseaseVo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

// TODO 公共模块新增类
public interface CategoryService extends IService<CategoryEntity> {
    List<CategoryEntity> getCategory();
    void removeNode(String id);

    void removeNode(String id, String label);

    void addParentDisease(String diseaseName);

    //    新增疾病管理模块
    List<CategoryEntity> getAllDisease();
    Integer addCategory(AddDiseaseVo addDiseaseVo);
    Result updateCategory(UpdateDiseaseVo updateDiseaseVo);
    void removeCategorys(List<String> deleteIds);

    List<CategoryEntity> getLevel2Label();
    List<CategoryEntity> getLabelsByPid(@Param("pid") String pid);

    //ssq
     List<CategoryEntity> getCategory(String uid);
    void changeStatus(CategoryEntity categoryEntity);
    List<CategoryEntity> getTaskCategory();



}

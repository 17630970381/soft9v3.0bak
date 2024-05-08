package com.cqupt.software_9.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// TODO 公共模块新增类

@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<CategoryEntity> {
    void removeNode(@Param("id") String id);


    void savetest(CategoryEntity categoryNode);

    Integer countDisease();

    Integer countTable();

    String isRepeatCategory(String label);


    List<CategoryEntity> getall();

    List<CategoryEntity> getLevel2Label();
    String getLabelByPid(@Param("pid") String pid);
    List<CategoryEntity> getLabelsByPid(@Param("pid") String pid);

    void updateTableNameByTableId(@Param("tableid") String tableid, @Param("tableName") String tableName, @Param("tableStatus") String tableStatus);


    //ssq
    void removeTable(@Param("label") String label);
    void changeStatusToShare(@Param("id") String id);

    void changeStatusToPrivate(@Param("id") String id);

    List<CategoryEntity> getSpDisease();
    List<CategoryEntity> getComDisease();

    String getParentTYpeById(String id);
}

package com.cqupt.software_9.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.common.MergeData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MergeDataMapper extends BaseMapper<MergeData> {

    List<MergeData> findByDiagnameAndAgeRange(String diagname, String startAge, String endAge);

    List<String> getAllDiagnoses();

    Integer getTotalDiseaseCount();

    Integer getDiseaseCount(String diagnosis);

    List<String> getAllUserBuiltTableNames();

    String getType(String tablename);

    Integer getPosNumber(@Param("tablename") String tablename, @Param("type") String type);

    Integer getNegNumber(@Param("tablename") String tablename, @Param("type") String type);
}

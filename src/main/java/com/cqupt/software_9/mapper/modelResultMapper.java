package com.cqupt.software_9.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.common.modelAndModelResultDTO;
import com.cqupt.software_9.entity.modelResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface modelResultMapper extends BaseMapper<modelResult> {
    boolean save(modelResult modelResult);

    boolean removeModelResult(String modelname);

    List<modelResult> getModelDetail(String modelname);


    List<Map<String,String>> getTableName();


    List<modelAndModelResultDTO> getModelResultAndModel(String modelname);

    List<Map<String, String>> getTableNamePre();
}

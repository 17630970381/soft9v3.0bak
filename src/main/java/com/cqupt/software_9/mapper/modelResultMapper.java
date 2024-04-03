package com.cqupt.software_9.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.entity.modelResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface modelResultMapper extends BaseMapper<modelResult> {
    boolean save(modelResult modelResult);

    boolean removeModelResult(String modelname);

    List<modelResult> getModelDetail(String modelname);


}

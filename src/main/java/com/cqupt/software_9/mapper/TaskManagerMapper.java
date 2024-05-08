package com.cqupt.software_9.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.entity.TaskManager;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskManagerMapper extends BaseMapper<TaskManager> {


    String getPublisherbumodelname(String modelname);

    void deleteByModelname(String modelname);
}

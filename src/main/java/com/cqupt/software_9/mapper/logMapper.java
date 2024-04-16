package com.cqupt.software_9.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.entity.User;
import com.cqupt.software_9.entity.log;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface logMapper extends BaseMapper<log> {


    List<log> getall();
}

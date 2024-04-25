package com.cqupt.software_9.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.common.DetailDTO;
import com.cqupt.software_9.entity.Detail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DetailMapper extends BaseMapper<Detail> {


    List<DetailDTO> getDetail(String modelname);

    List<DetailDTO> getAll(String modelname);
}

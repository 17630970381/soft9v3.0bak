package com.cqupt.software_9.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.entity.Nodes;
import com.cqupt.software_9.entity.Relationships;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NodesMapper extends BaseMapper<Nodes> {
    List<Nodes> getAllNodes();

    List<Relationships> getRelationships();
}

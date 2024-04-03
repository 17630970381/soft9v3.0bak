package com.cqupt.software_9.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.entity.TableDescribeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

// TODO 公共模块新增类

@Mapper
@Repository
public interface TableDescribeMapper extends BaseMapper<TableDescribeEntity> {
}

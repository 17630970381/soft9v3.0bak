package com.cqupt.software_9.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqupt.software_9.common.Result;
import com.cqupt.software_9.entity.TableDescribeEntity;
import com.cqupt.software_9.mapper.TableDescribeMapper;
import com.cqupt.software_9.service.TableDescribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

// TODO 公共模块新增类

@RestController
@RequestMapping("/TableDescribe")
public class TableDescribeController {
    @Autowired
    TableDescribeService tableDescribeService;

    @Resource
    private TableDescribeMapper tableDescribeMapper;

    @GetMapping("/tableDescribe")
    public Result<TableDescribeEntity> getTableDescribe(@RequestParam("id") String id){ // 参数表的Id
        TableDescribeEntity tableDescribeEntity = tableDescribeMapper.selectOne(new QueryWrapper<TableDescribeEntity>().eq("table_id", id));
        System.out.println("数据为："+ JSON.toJSONString(tableDescribeEntity));
        return Result.success("200",tableDescribeEntity);
    }


}

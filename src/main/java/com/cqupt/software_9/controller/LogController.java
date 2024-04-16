package com.cqupt.software_9.controller;

import com.cqupt.software_9.common.Result;
import com.cqupt.software_9.entity.User;
import com.cqupt.software_9.entity.log;
import com.cqupt.software_9.mapper.logMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequestMapping("log")
@RestController
public class LogController {
    @Autowired
    private logMapper logmapper;


    //获取全部日志信息
    @GetMapping("getLogAll")
    public Result<List<log>> getall(){
        return Result.success("200",logmapper.getall());
    }




}

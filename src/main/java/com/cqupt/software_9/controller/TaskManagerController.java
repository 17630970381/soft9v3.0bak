package com.cqupt.software_9.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqupt.software_9.common.Result;
import com.cqupt.software_9.entity.TaskManager;
import com.cqupt.software_9.mapper.TaskManagerMapper;
import com.cqupt.software_9.service.TaskManagerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/TaskManager")
public class TaskManagerController {

    @Resource
    private TaskManagerMapper taskManagerMapper;

    @Resource
    private TaskManagerService taskManagerService;


    //分页
    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String disease,
                               @RequestParam String modelname,
                               @RequestParam String publisher){
        QueryWrapper<TaskManager> queryWrapper = new QueryWrapper<TaskManager>().orderByDesc("id");
        queryWrapper.like(StringUtils.isNotBlank(disease),"diseasename",disease);
        queryWrapper.like(StringUtils.isNotBlank(modelname),"modelname",modelname);
        queryWrapper.like(StringUtils.isNotBlank(publisher),"publisher",publisher);
        Page<TaskManager> page = taskManagerService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(page);
    }

}

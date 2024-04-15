package com.cqupt.software_9.controller;

import com.cqupt.software_9.common.modelAndModelResultDTO;
import com.cqupt.software_9.entity.modelResult;
import com.cqupt.software_9.mapper.modelResultMapper;
import com.cqupt.software_9.service.modelResultService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RequestMapping("/modelResult")
@RestController
public class modelResultController {
    @Resource
    private modelResultMapper modelResultMapper;

    @Resource
    private modelResultService modelResultService;
    @GetMapping("/getTableName")
    public List<Map<String,String>> getTableName(){
        return modelResultMapper.getTableName();
    }


    /**
     * 获取所有模型结果
     * @return
     */
    @GetMapping("/getModelResult")
    public List<modelResult> getAlAndTable(){
        return modelResultService.list();
    }

    @GetMapping("/getModelResultAndModel/{modelname}")
    public List<modelAndModelResultDTO> getModelResultAndModel(@PathVariable("modelname") String modelname){
        return modelResultMapper.getModelResultAndModel(modelname);
    }
}

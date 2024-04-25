package com.cqupt.software_9.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqupt.software_9.entity.Detail;
import com.cqupt.software_9.mapper.DetailMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/Detail")
@RestController
public class DetailController {

    @Resource
    private DetailMapper detailMapper;



    @GetMapping("/getAll/{modelname}")
    public List<Detail> getAll(@PathVariable("modelname") String modelname){
        QueryWrapper<Detail> wrapper = new QueryWrapper<>();
        wrapper.eq("modelname",modelname);
        return detailMapper.selectList(wrapper);
    }
}

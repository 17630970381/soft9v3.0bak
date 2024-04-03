package com.cqupt.software_9.controller;

import com.cqupt.software_9.common.Result;
import com.cqupt.software_9.mapper.DataManagerMapper;
import com.cqupt.software_9.mapper.MergeDataMapper;
import com.cqupt.software_9.mapper.ModelMapper;
import com.cqupt.software_9.service.DataManagerService;
import com.cqupt.software_9.service.MergeDataService;
import com.cqupt.software_9.service.ModelService;
import com.cqupt.software_9.service.StasticOneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/merge")
public class MergeDataController {
    @Autowired
    private MergeDataService mergeDataService;
    @Autowired
    private StasticOneService stasticOneService;
    @Resource
    private DataManagerMapper dataManagerMapper;

    @Resource
    private DataManagerService dataManagerService;
    @Resource
    private ModelService modelService;
    @Resource
    private ModelMapper modelMapper;

    @Resource
    private MergeDataMapper mergeMapper;
    /**
     * 年龄-疾病趋势
     * @return
     */
    @GetMapping("mergedata")
    public Result<Map<String, Map<Integer, Long>>> countPatientsByAgeRangeAndDiagname() {
        Map<String, Map<Integer, Long>> countMap = mergeDataService.countPatientsByAgeRangeForAllDiagnoses();
        return Result.success("200",countMap);
    }


    /**
     * 获取顶部数据
     */
    @GetMapping("getTopData")
    public Result<Map<String,String>> getTopData(){
        Map<String, String> topData = new HashMap<>();
        String tableNum = dataManagerMapper.getTableNum().toString();
        topData.put("specialityCount",tableNum);
        String sampleNum = dataManagerMapper.getSampleNum().toString();
        topData.put("sampleCount",sampleNum);
        String startTime = modelMapper.getStartTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime dateTime = LocalDateTime.parse(startTime, formatter);
        // 提取日期部分
        String datePart = dateTime.toLocalDate().toString();
        topData.put("startTime",datePart);
        String modelNum = modelMapper.getModelNum().toString();
        topData.put("modelCount",modelNum);
        return Result.success(topData);
    }

    /**
     * 疾病占比
     */
    @GetMapping("/getDiseaseRates")
    public Result getDiseaseRates() {
        Map<String, Integer> diseaseRates = mergeDataService.getDiseaseRates();
        return Result.success(200, "获取疾病患病率成功", diseaseRates);
    }

    @GetMapping("/getDiagName")
    public Result getDiagName(){
        List<String> diagName = modelMapper.getDiagName();
        return Result.success(200,"获取疾病名称成功",diagName);
    }

    /**
     *柱状图
     *
     */
    @GetMapping("/get_pos_neg")
    public Result getPosAndNeg(){
        List<String> userbuilt_table_names = mergeMapper.getAllUserBuiltTableNames();
        Map<String, Map<String,Integer>>  pos_neg_map = new HashMap<>();
        for(int i=0;i<userbuilt_table_names.size();i++){
            String tablename = userbuilt_table_names.get(i);
            String type = mergeMapper.getType(tablename);
            Integer posNumber = mergeMapper.getPosNumber(tablename,type);
            Integer negNumber = mergeMapper.getNegNumber(tablename,type);
            Map<String,Integer> temp = new HashMap<>();
            temp.put("pos",posNumber);
            temp.put("neg",negNumber);
            pos_neg_map.put(tablename,temp);
        }
        //获取疾病所有表名
        return Result.success(200,"success",pos_neg_map);
    }



}

package com.cqupt.software_9.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqupt.software_9.common.R;
import com.cqupt.software_9.common.UploadResult;
import com.cqupt.software_9.entity.*;
import com.cqupt.software_9.mapper.DataManagerMapper;
import com.cqupt.software_9.mapper.ModelMapper;
import com.cqupt.software_9.service.DataTableManagerService;
import com.cqupt.software_9.service.FileService;
import com.cqupt.software_9.service.ModelService;
import com.cqupt.software_9.service.modelResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/Model")
@RestController
public class ModelController {


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Resource
    private ModelMapper modelMapper;

    @Autowired
    private modelResultService modelResultService;
    @Resource
    private ModelService modelService;

    @Resource
    private DataTableManagerService dataTableManagerService;

    @Resource
    private FileService fileService;

    @Resource
    private DataManagerMapper dataManagerMapper;

    @Resource
    private com.cqupt.software_9.mapper.tTableManagerMapper tTableManagerMapper;



    @GetMapping("/getall")
    public List<Model> getallmodel(){
        return modelMapper.getallmodel();
    }





    /**
     *
     * @param file 前端传来的文件
     * @param modelname 训练名称
     * @param diseasename   疾病名称
     * @return
     * @throws Exception
     */
    @PostMapping("/file")
    public UploadResult upload(@RequestParam("file") MultipartFile file,
                               @RequestParam("modelname") String modelname ,
                               @RequestParam("diseasename") String diseasename,
                               @RequestParam("publisher") String publisher,
                               @RequestParam("uid") Integer uid) throws Exception {

        try {
            String fileName = file.getOriginalFilename().replace(".csv", "");
            QueryWrapper<DataManager> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("tablename", fileName);
            Long count = dataManagerMapper.selectCount(queryWrapper);
            if (count > 0) {
                return new UploadResult(null, 400, null, null, null);
            }
            UploadResult res =  fileService.fileUpload(file, modelname,diseasename,publisher,uid);
            return res;
        } catch (Exception e) {
            UploadResult result =new UploadResult();
            result.setE(e);
            return result;
        }
    }

    /**
     * 通过uid查询该uid下的所有表
     * @param uid
     * @return
     */
    @GetMapping("/tableName/{uid}")
    public List<DataManager> getTableName(@PathVariable("uid") Integer uid) {
        return dataManagerMapper.getTableNameByUiD(uid);
    }

    /**
     * 通过tablename查询该数据表中的所有特征值
     * @param tablename
     * @return
     */
    @GetMapping("/getFeature/{tablename}")
    public List<tTableField> getFeature(@PathVariable("tablename") String tablename){
        return tTableManagerMapper.getFeatureByTableName(tablename);
    }


    /**
     * 通过uid和模型名称查询该用户所选模型所需要的特征等数据
     */
    @GetMapping("/getModelFeatureByUidAndModelName/{uid}/{modelname}")
    public List<Model> getModelFeatureByUidAndModelName(@PathVariable("uid") Integer uid,
                                       @PathVariable("modelname") String modelname){
        return modelMapper.getModelFeatureByUidAndModelName(uid, modelname);
    }

    /**
     * 通过算法名查找算法简介和参数
     */
    @GetMapping("getAlgorithm")
    public List<publicAl> getAlgorithmByAlgorithmName(){
        return modelMapper.getAlgorithmByAlgorithmName();
    }

    /**
     * 接收前端传过来的表名，所选目标列和特征列和所选算法及算法超参
     */
    @PostMapping("/trainAl")
    public Map<String, List<modelResult>> trainAl(@RequestBody trainAl trainAl){

        return modelService.trainModel(trainAl);
    }


    /**
     * 数据选择模块中，通过病种查询对应的数据表
     */
    @GetMapping("/getTableByDisease/{diseasename}")
    public  List<String> getTableByDisease(@PathVariable("diseasename")String diseasename){
        return dataManagerMapper.getTableByDisease(diseasename);
    }

    /**
     * 根据数据表明获取到列名作为特证名
     */
    @GetMapping("/getFeaByTableName/{tableName}")
    public List<String> getFeaByTableName(@PathVariable("tableName")String tableName){
        return modelMapper.getFeaByTableName(tableName);
    }


    @GetMapping("/getInfoByTableName/{tableName}")
    public R<List<Map<String,Object>>> getInfoByTableName(@PathVariable("tableName") String tableName){
        tableName = tableName.replace("\"", "");
        List<Map<String, Object>> res = dataTableManagerService.getInfoByTableName(tableName);
        return new R<>(200, "成功", res);
    }

    /**
     * 获取数据表的基本信息
     */
    @GetMapping("/getTableInfo/{tableName}")
    public List<DataManager> getTableInfo(@PathVariable("tableName") String tableName) {
        return  dataManagerMapper.getTableInfo(tableName);
    }


    /**
     * 计算表中，每一个特证的缺失值比例
     */
    @GetMapping("getMissingRates/{tableName}")
    public Map<String, Integer> getMissingRates(@PathVariable("tableName") String tableName) {
        Map<String, Integer> missingRates = new HashMap<>();
        int totalRows = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName, Integer.class);

        // 动态获取特征列的名称
        List<String> featureNames = jdbcTemplate.queryForList(
                "SELECT column_name FROM information_schema.columns WHERE table_name = ? AND table_schema = 'public'",
                String.class, tableName);

        // 对于每个特征，计算其缺失率
        for (String feature : featureNames) {
            int missingCount = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM " + tableName + " WHERE \"" + feature + "\" IS NULL",
                    Integer.class);
            missingRates.put(feature, (int) ((double) missingCount / totalRows * 100));
        }
        return missingRates;
    }

    @PostMapping("modelResult")
    public boolean insertModelResult(@RequestBody modelResult modelResult) {
        return modelResultService.saveOrUpdate(modelResult);
    }

    @PostMapping("/baseInfo")
    public boolean insertBaseInfo(@RequestBody Model model) {
        return modelService.saveOrUpdate(model);
    }

    /**
     * 查询数据库中模型吗是否重复
     */
    @GetMapping("/isRepeatModel/{modelname}")
    public boolean isRepeatModel(@PathVariable("modelname") String modelname) {
       String i = modelMapper.isRepeatModel(modelname);
       System.out.println(i);
       if(i == null) {
           return true;
       }else {
           return false;
       }
    }

    /**
     * 查询数据表的样本数量
     */
    @GetMapping("/getNumber/{tableName}")
    public List<Integer> getNumber(@PathVariable("tableName") String tablename){
        Integer row = dataManagerMapper.getRow(tablename);
        Integer colum = dataManagerMapper.getColumn(tablename);
        List<Integer> list = new ArrayList<>();
        list.add(row);
        list.add(colum);
        return list;
    }

}
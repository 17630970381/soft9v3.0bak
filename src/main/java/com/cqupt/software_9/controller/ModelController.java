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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RequestMapping("/Model")
@RestController
public class ModelController {
    @Resource
    private ModelMapper modelMapper;

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

    @PostMapping("/baseInfo")
    public boolean insertBaseInfo(@RequestBody Model model) {
        return modelService.saveOrUpdate(model);
    }

    @GetMapping("/getInfoByTableName/{tableName}")
    public R<List<Map<String,Object>>> getInfoByTableName(@PathVariable("tableName") String tableName){
        tableName = tableName.replace("\"", "");
        List<Map<String, Object>> res = dataTableManagerService.getInfoByTableName(tableName);
        return new R<>(200, "成功", res);
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
    public trainAl trainAl(@RequestBody trainAl trainAl){
        modelService.trainModel(trainAl);
        return null;
    }

}
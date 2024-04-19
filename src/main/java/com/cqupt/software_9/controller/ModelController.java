package com.cqupt.software_9.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqupt.software_9.common.*;
import com.cqupt.software_9.entity.*;
import com.cqupt.software_9.mapper.*;
import com.cqupt.software_9.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

@RequestMapping("/Model")
@RestController
public class ModelController {


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Resource
    private ModelMapper modelMapper;

    @Autowired
    private modelResultService modelResultService;

    @Autowired
    private modelResultMapper modelResultMapper;

    @Autowired
    private UserLogService userLogService;

    @Resource
    private UserService userService;


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

    @Resource
    private com.cqupt.software_9.service.tTableManagerService  tTableManagerService;

    @Resource
    private MergeDataMapper mergeDataMapper;

    @Resource
    private UserMapper userMapper;

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
            missingRates.put(feature, 100 - (int) ((double) missingCount / totalRows * 100));
        }
        return missingRates;
    }


    @PostMapping("/modelResult")
    public boolean insertModelResult(@RequestBody ModelRequestData modelRequestData) {
        return modelService.insertModelResultAndModel(modelRequestData);
    }

    @Transactional
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

    /**
     * 按照ModelDTO返回model表中的数据
     */
    @GetMapping("/getModel")
    public List<ModelDTO> getModel(){
        return modelMapper.getModel();
    }

    /**
     * 删除模型
     * @param modelname
     * @return
     */
    @PutMapping("/remove/{modelname}")
    public boolean ModelRemove( @PathVariable("modelname") String modelname){
        boolean a = modelMapper.removeModel(modelname);
        boolean b = modelResultMapper.removeModelResult(modelname);

        //  操作日志记录

        UserLog userLog = new UserLog();
        String username = modelMapper.getPublisherbumodelname(modelname);
        userLog.setUsername(username);
        String uid = userMapper.getUidByUsername(username);

        // userLog.setId(1);
        userLog.setUid(uid);
        if (a && b){
            userLog.setOpType("用户删除模型"+modelname+"成功");
            userLogService.save(userLog);
            return true;
        }else {
            return false;
        }

    }

    /**
     * 根据模型名查到模型详细信息
     */
    @GetMapping("/getModelDetail/{modelname}")
    public List<modelResult> getModelDetail(@PathVariable("modelname")String modelname){
        return modelResultMapper.getModelDetail(modelname);
    }

    /**
     * 分页模糊查询
     * @param pageNum
     * @param pageSize
     * @param disease
     * @param modelname
     * @return
     */
    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String disease,
                               @RequestParam String modelname,
                               @RequestParam String publisher){
        QueryWrapper<Model> queryWrapper = new QueryWrapper<Model>().orderByDesc("taskid");
        queryWrapper.like(StringUtils.isNotBlank(disease),"diseasename",disease);
        queryWrapper.like(StringUtils.isNotBlank(modelname),"modelname",modelname);
        queryWrapper.like(StringUtils.isNotBlank(publisher),"publisher",publisher);
        Page<Model> page = modelService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(page);
    }

    @GetMapping("/getDisease")
    public List<String> getDisease(){
        return modelMapper.getDisease();
    }

    /**
     * 根据模型名查询模型所用特征
     */
    @GetMapping("/getFea/{modelname}")
    public String getFea(@PathVariable String modelname){
        return modelMapper.getFea(modelname);
    }


    @GetMapping("/whetherexists/{modelname}")
    public Result whetherexists(@PathVariable("modelname") String modelname){
        List<String> fields = tTableManagerService.getFiledSByTableName("merge");
        String feat = modelService.getfeabymodelname(modelname);
        String[] splitArray = feat.split(",");
        List<String> feats = Arrays.asList(splitArray);
//        int  res = 1;
//        for (String element : feats) {
//            if (!fields.contains(element)) {
//                res = 0;
//            }
//        }


        System.out.println("feats");
        System.out.println(feats);
        System.out.println("fields");
        System.out.println(fields);
        int res = 1;
        for (String element : feats) {
            boolean found = false;
            for (String field : fields) {
                if (element.equalsIgnoreCase(field)) { // 忽略大小写进行匹配
                    found = true;
                    break;
                }
            }
            if (!found) {
                res = 0;
                break; // 如果有一个字段不匹配，则可以直接跳出循环
            }
        }
        return new Result(res,"成功",200);
    }

    /**
     * 获取merge表病人信息
     *
     *
     */
    @GetMapping("/upallmerge")
    public R<List<MergeList>> upallmerge(){
        return new R(200,"成功",mergeDataMapper.upallmerge());
    }

    /**
     * 查询模型总数
     */
    @GetMapping("/getModelNum")
    public Integer getModelNum(){
        return modelMapper.getModelNum();
    }
}
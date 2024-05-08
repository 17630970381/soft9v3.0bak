package com.cqupt.software_9.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.software_9.entity.*;
import com.cqupt.software_9.mapper.*;
import com.cqupt.software_9.service.ModelService;
import com.cqupt.software_9.service.Request.RuntimeTaskRequest;
import com.cqupt.software_9.service.Response.OnlineServiceResponse;
import com.cqupt.software_9.service.Response.RuntimeTaskResponse;
import com.cqupt.software_9.service.RuntimeTaskService;
import com.cqupt.software_9.tool.PythonRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ModelServiceImpl extends ServiceImpl<ModelMapper, Model> implements ModelService {

    @Resource
    private RuntimeTaskService runtimeTaskService;

    @Autowired
    private PythonRun pythonRun; // 注入 PythonRun 类的实例

    @Autowired
    private modelResult modelResult;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private modelResultMapper modelResultMapper;

    @Resource
    private UserLogMapper userLogMapper;
    @Resource
    private UserMapper userMapper;

    @Resource
    private TaskManagerMapper taskManagerMapper;

    @Override
    public Map<String, List<modelResult>> trainModel(trainAl trainAl) {
        UserLog userLog = new UserLog();
        userLog.setOpType("进行在线训练模型");
        userLogMapper.insert(userLog);
        Map<String, List<modelResult>> resultMap = new HashMap<>();
        // 调用训练模型算法
        String tableName = trainAl.getTableName();
        String target = trainAl.getTarget();
        String[] fea = trainAl.getFea();
        List<Map<String, Map<String, String>>> completeParameter = trainAl.getCompleteParameter();
        for (Map<String, Map<String, String>> algorithmMap : completeParameter) {
            String algorithmName = algorithmMap.keySet().iterator().next();
            Map<String, String> algorithmAttributes = algorithmMap.get(algorithmName);
            List<String> args = new ArrayList<>();
            args.add(tableName);
            args.add(target);
            for (String feature : fea) {
                args.add(feature);
            }
            args.add(algorithmName);
            for (Map.Entry<String, String> entry : algorithmAttributes.entrySet()) {
                args.add(entry.getKey() + "=" + entry.getValue());
            }

            try {
                // 执行 Python 脚本并获取结果
                String result = pythonRun.publicAl("E:\\soft\\software9-3\\software9\\src\\main\\resources\\Algorithm\\python\\publicAl.py",
                        tableName, target, fea, algorithmName, algorithmAttributes);


                // 定义正则表达式模式
                String patternString = "\\{.*\\}";
                String pathPatternString = "E:\\\\[^\\s]*";


                // 编译正则表达式模式
                Pattern pattern = Pattern.compile(patternString);
                Pattern pathPattern = Pattern.compile(pathPatternString);

                // 创建匹配器
                Matcher matcher = pattern.matcher(result);
                Matcher pathMatcher = pathPattern.matcher(result);

                // 查找匹配的字符串
                String evaluate = "";
                String picturePath = "";
                String pklPath = "";

                if (matcher.find()) {
                    evaluate = matcher.group();
                }

                int count = 0;
                while (pathMatcher.find()) {
                    String path = pathMatcher.group();
                    if (count == 0) {
                        picturePath = path;
                    } else if (count == 1) {
                        pklPath = path;
                    }
                    count++;
                }

                // 输出结果
                System.out.println("评估信息: " + evaluate);
                System.out.println("图片路径: " + picturePath);
                System.out.println("模型路径: " + pklPath);
                modelResult modelResult = new modelResult(); // 创建 modelResult 对象

                modelResult.setEvaluate(evaluate);
                modelResult.setPicture(picturePath);
                modelResult.setPkl(pklPath);

                List<modelResult> resultList = new ArrayList<>();
                resultList.add(modelResult);
                resultMap.put(algorithmName, resultList);
                System.out.println(resultMap);
            } catch (Exception e) {
                e.printStackTrace();
                // 处理异常
            }
        }

        return resultMap;
    }

    @Override
    public List<Model> upall() {
        return null;
    }

    @Override
    public boolean insertModelResultAndModel(ModelRequestData modelRequestData) throws Exception {
        modelResult modelResult = new modelResult();
        Model model = new Model();
        String modelname = modelRequestData.getModelname() + "-" + modelRequestData.getAl();
        System.out.println(modelname);
        modelResult.setPkl(modelRequestData.getPkl());
        modelResult.setModelname(modelname);
        modelResult.setEvaluate(modelRequestData.getEvaluate());
        modelResult.setPicture(modelRequestData.getPicture());
        modelResult.setUid(modelRequestData.getUid());
        modelResult.setAl(modelRequestData.getAl());
        modelResult.setTablename(modelRequestData.getTablename());
        modelResult.setDiseasename(modelRequestData.getDiseasename());

        model.setModelname(modelname);
        model.setDiseasename(modelRequestData.getDiseasename());
        model.setPublisher(modelRequestData.getPublisher());
        model.setRemarks(modelRequestData.getRemarks());
        model.setUid(modelRequestData.getUid());
        model.setModeurl(modelRequestData.getPkl());
        model.setFeature(modelRequestData.getFeature());

        if(isReapt(modelRequestData)){
            TaskManager taskManager = new TaskManager();
            taskManager.setMostacc(modelRequestData.getMostacc());
            taskManager.setModelname(modelRequestData.getModelname());
            taskManager.setAlname(modelRequestData.getAlname());
            taskManager.setDiseasename(modelRequestData.getDiseasename());
            taskManager.setPublisher(modelRequestData.getPublisher());
            taskManager.setTablename(modelRequestData.getTablename());
            taskManagerMapper.insert(taskManager);
        }
        int a = modelMapper.insert(model);
        int b = modelResultMapper.insert(modelResult);
        saveModelResult(modelRequestData);
        if(a*b !=0){
            UserLog userLog = new UserLog();
            userLog.setUid(modelRequestData.getUid());
            userLog.setOpType("保存模型成功");
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("uid",modelRequestData.getUid());
            User user = userMapper.selectOne(wrapper);
            userLog.setUsername(modelRequestData.getPublisher());
            userLogMapper.insert(userLog);

            return true;
        }else {
            UserLog userLog2 = new UserLog();
            userLog2.setUid(modelRequestData.getUid());
            userLog2.setOpType("保存模型失败");
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("uid",modelRequestData.getUid());
            User user = userMapper.selectOne(wrapper);
            userLog2.setUsername(modelRequestData.getPublisher());
            userLogMapper.insert(userLog2);
            return false;
        }
    }

    protected  boolean isReapt(ModelRequestData modelRequestData){
        String modelname = modelRequestData.getModelname();
        QueryWrapper<TaskManager> wrapper = new QueryWrapper<>();
        wrapper.eq("modelname",modelname);
        TaskManager taskManager = taskManagerMapper.selectOne(wrapper);
        if(taskManager != null){
            return false;
        }else {
            return true;
        }
    }

    protected boolean saveModelResult(ModelRequestData modelRequestData) throws Exception {
        List<String> args=new LinkedList<>();
        String feature = "--feature=" + modelRequestData.getFeature();
        String target = "--target=" + modelRequestData.getTarget();
        String p_calculation_rates = "--p_calculation_rates="+ modelRequestData.getP_calculation_rates();
        String b_calculation_rates = "--b_calculation_rates="+ modelRequestData.getB_calculation_rates();
        String modelname = "--modelname="+ modelRequestData.getModelname().concat(modelRequestData.getAl());;
        String tablename = "--tablename="+ modelRequestData.getTablename();
        args.add(feature);
        args.add(p_calculation_rates);
        args.add(b_calculation_rates);
        args.add(modelname);
        args.add(tablename);
        args.add(target);
        System.out.println(args);
        RuntimeTaskRequest runtimeTaskRequest=new RuntimeTaskRequest();
        runtimeTaskRequest.setPyPath("E:\\soft\\software9-3\\software9\\src\\main\\resources\\Algorithm\\python\\detail.py");
        OnlineServiceResponse response=new OnlineServiceResponse();
        runtimeTaskRequest.setArgs(args);
        RuntimeTaskResponse taskResponse=runtimeTaskService.submitTask(runtimeTaskRequest);
        response.setRes(taskResponse.getRes());
        return true;

    }

    @Override
    public String getfeabymodelname(String modelname) {
        return modelMapper.getFeaBymodelName(modelname);
    }




}



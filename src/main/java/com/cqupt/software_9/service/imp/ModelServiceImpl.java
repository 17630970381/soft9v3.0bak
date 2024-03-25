package com.cqupt.software_9.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.software_9.entity.Model;
import com.cqupt.software_9.entity.modelResult;
import com.cqupt.software_9.entity.trainAl;
import com.cqupt.software_9.mapper.ModelMapper;
import com.cqupt.software_9.service.ModelService;
import com.cqupt.software_9.tool.PythonRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ModelServiceImpl extends ServiceImpl<ModelMapper, Model> implements ModelService {


    @Autowired
    private PythonRun pythonRun; // 注入 PythonRun 类的实例

    @Autowired
    private modelResult modelResult;

    @Override
    public Map<String, List<modelResult>> trainModel(trainAl trainAl) {

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
}



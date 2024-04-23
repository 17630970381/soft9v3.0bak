package com.cqupt.software_9.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqupt.software_9.entity.Model;
import com.cqupt.software_9.entity.ModelRequestData;
import com.cqupt.software_9.entity.modelResult;
import com.cqupt.software_9.entity.trainAl;

import java.util.List;
import java.util.Map;

public interface ModelService extends IService<Model> {

    Map<String, List<modelResult>> trainModel(trainAl trainAl);


    List<Model> upall();


    boolean insertModelResultAndModel(ModelRequestData modelRequestData) throws Exception;

    String getfeabymodelname(String modelname);


}

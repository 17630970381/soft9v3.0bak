package com.cqupt.software_9.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqupt.software_9.common.MergeData;

import java.util.Map;


public interface MergeDataService extends IService<MergeData> {

     Map<String, Map<Integer, Long>> countPatientsByAgeRangeForAllDiagnoses();

    Map<String, Integer> getDiseaseRates();
}

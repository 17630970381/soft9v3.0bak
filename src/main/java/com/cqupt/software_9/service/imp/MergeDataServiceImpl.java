package com.cqupt.software_9.service.imp;

import com.cqupt.software_9.common.MergeData;
import com.cqupt.software_9.mapper.MergeDataMapper;
import com.cqupt.software_9.service.Adapter.MergeDataServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MergeDataServiceImpl extends MergeDataServiceAdapter {

    @Autowired
    private MergeDataMapper mergeDataMapper;

    public Map<String, Map<Integer, Long>> countPatientsByAgeRangeForAllDiagnoses() {
        Map<String, Map<Integer, Long>> diagnosisCountMap = new HashMap<>();

        // 获取所有疾病名
        List<String> diagnoses = mergeDataMapper.getAllDiagnoses();

        // 遍历所有疾病
        for (String diagnosis : diagnoses) {
            Map<Integer, Long> countMap = new HashMap<>();

            // 对当前疾病进行统计
            for (int i = 0; i <= 100; i += 10) {
                int startAge = i;
                int endAge = i + 9;
                List<MergeData> mergeDataList = mergeDataMapper.findByDiagnameAndAgeRange(diagnosis, String.valueOf(startAge), String.valueOf(endAge));
                long count = mergeDataList.size();
                countMap.put(startAge, count);
            }

            // 将当前疾病的统计结果加入到结果集中
            diagnosisCountMap.put(diagnosis, countMap);
        }

        return diagnosisCountMap;
    }

    @Override
    public Map<String, Integer> getDiseaseRates() {
        Map<String, Integer> diseaseRates = new HashMap<>();

        Integer totalDiseaseCount = mergeDataMapper.getTotalDiseaseCount();
        if (totalDiseaseCount == null || totalDiseaseCount == 0) {
            return diseaseRates; // 返回空的结果
        }

        List<String> allDiagnoses = mergeDataMapper.getAllDiagnoses();
        for (String diagnosis : allDiagnoses) {
            Integer diseaseCount = mergeDataMapper.getDiseaseCount(diagnosis);
            if (diseaseCount != null) {
                diseaseRates.put(diagnosis, diseaseCount);
            }
        }

        return diseaseRates;
    }
}

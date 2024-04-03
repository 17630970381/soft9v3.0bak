package com.cqupt.software_9.controller;

import com.cqupt.software_9.entity.Patient;
import com.cqupt.software_9.entity.PatientHeartDisease;
import com.cqupt.software_9.mapper.DiseasesMapper;
import com.cqupt.software_9.mapper.ModelMapper;
import com.cqupt.software_9.service.PatientHeartDiseaseService;
import com.cqupt.software_9.service.PatientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/Patient")

@RestController
public class PatientController {
    @Resource
    private  PatientService patientService;

    @Resource
    private PatientHeartDiseaseService patientHeartDiseaseService;

    @Resource
    private DiseasesMapper diseasesMapper;

    @Resource
    private ModelMapper modelMapper;
    /**
     * Author:陈鹏
     *时间：2023.6.23
     *查询病人基础信息
     *@return
     * */
    @GetMapping("/patient")
    public List<Patient> getAllPatient() {
        return patientService.getAllPatients();
    }


    /**
     * Author:陈鹏
     *时间：2023.6.23
     *查询病人完整信息
     *@return
     * */
    @GetMapping("/patients")
    public List<Map<String, Object>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Patient patient : patients) {
            List<PatientHeartDisease> heartDiseases = patientHeartDiseaseService.getHeartDiseaseByPatientId(patient.getPatientId());
            Map<String, Object> mergedData = new HashMap<>();

            mergedData.putAll(patient.toMap()); // Add patient fields

            if (!heartDiseases.isEmpty()) {
                PatientHeartDisease heartDisease = heartDiseases.get(0); // Assuming only one heart disease per patient
                mergedData.putAll(heartDisease.toMap()); // Add heartDisease fields
            }

            result.add(mergedData);
        }

        System.out.println(result);
        return result;
    }


    /**
     * 通过用户名和用户id获取已训练好的模型信息
     */
    @GetMapping("getAllModelByPublisherAndUid/{publisher}/{uid}/{diseasename}")
    public List<String> getAllModelByPublisherAndUid(@PathVariable("publisher") String publisher,
                                                     @PathVariable("uid") Integer uid,
                                                     @PathVariable("diseasename") String diseasename){
          return modelMapper.getAllModelByPublisherAndUid(publisher,uid,diseasename);
    }

    /**
     * 用过模型名称查询该模型保存的地址
     */
    @GetMapping("getModelPathByModelName/{modelname}")
    public String getModelPathByModelName(@PathVariable("modelname") String modelname){
        return modelMapper.getModelPathByModelName(modelname);
    }

    /**
     * 根据疾病获得病灶部位
     */
    @GetMapping("getPartByDisease/{diseasename}")
    public String getPartByDisease(@PathVariable("diseasename") String diseasename){
        return diseasesMapper.getPartByDisease(diseasename);
    }

    /**
     * 根据疾病获得建议
     */
    @GetMapping("getPrevent/{diseasename}")
    public String getPrevent(@PathVariable("diseasename") String diseasename){
        return diseasesMapper.getPrevent(diseasename);
    }
}
package com.cqupt.software_9.service.imp;

import com.cqupt.software_9.entity.FieldManagementEntity;
import com.cqupt.software_9.mapper.FieldManagementMapper;
import com.cqupt.software_9.service.Adapter.FieldManagementServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldManagementServiceImpl extends FieldManagementServiceAdapter {
    @Autowired
    private FieldManagementMapper fieldManagementMapper;

    @Override
    public List<FieldManagementEntity> getFiledByDiseaseName(String diseaseName) {

        List<FieldManagementEntity> res = fieldManagementMapper.getFiledByDiseaseName(diseaseName);

        return res;
    }

    @Override
    public void updateFieldsByDiseaseName(String diseaseName, List<String> fields) {

        fieldManagementMapper.updateFieldsByDiseaseName(diseaseName, fields);
    }
}

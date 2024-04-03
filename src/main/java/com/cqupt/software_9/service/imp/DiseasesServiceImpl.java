package com.cqupt.software_9.service.imp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqupt.software_9.entity.Diseases;
import com.cqupt.software_9.mapper.DiseasesMapper;
import com.cqupt.software_9.service.Adapter.DiseasesServiceAdapter;
import com.cqupt.software_9.service.Request.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DiseasesServiceImpl extends DiseasesServiceAdapter {
    @Resource
    private DiseasesMapper diseasesMapper;

    @Override
    public IPage<Diseases> selectDiseasesPage(Query queryDTO) {
        Page<Diseases> page=new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return diseasesMapper.selectDiseasesPage(page,queryDTO.getKeyword());
    }

    @Override
    public List<String> getInfobycode(String code) {
        List<String> comment = diseasesMapper.getInfobycode(code);
        return comment;
    }

    @Override
    public List<Diseases> getAllDiseases() {
        return diseasesMapper.findAll();
    }

    @Override
    public Diseases findByCode(String code) {
        return diseasesMapper.findByCode(code);
    }
    @Override
    public List<Diseases> findBypart_name(String partName) {
        return diseasesMapper.findBypart_name(partName);
    }

    @Override
    public String findpartNamebyCode(String code) {
        return diseasesMapper.findpartNamebycode(code);
    }

    @Override
    public String findnamebyCode(String code) {
        return diseasesMapper.findnamebycode(code);
    }
    @Override
    public String findsympbyCode(String code) {
        return diseasesMapper.findsympbycode(code);
    }
    @Override
    public String finddptmentbyCode(String code) {
        return diseasesMapper.finddptmentbycode(code);
    }
    @Override
    public String findpreventbyCode(String code) { return diseasesMapper.findpreventbycode(code);
    }
}

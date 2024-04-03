package com.cqupt.software_9.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqupt.software_9.entity.Diseases;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
@Mapper
public interface DiseasesMapper extends BaseMapper<Diseases> {
    IPage<Diseases> selectDiseasesPage(Page<Diseases> page, String keyword);

    List<String> getInfobycode(String code);
    List<Diseases> findAll();
    Diseases findByCode(String code);
    List<Diseases> findBypart_name(String partName);

    String findpartNamebycode(String code);
    String findnamebycode(String code);
    String findsympbycode(String code);
    String findpreventbycode(String code);
    String finddptmentbycode(String code);

    String getPartByDisease(String diseasename);

    String getPrevent(String diseasename);
}

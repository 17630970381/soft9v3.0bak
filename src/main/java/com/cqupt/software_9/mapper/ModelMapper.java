package com.cqupt.software_9.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.entity.Model;
import com.cqupt.software_9.entity.publicAl;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ModelMapper extends BaseMapper<Model> {
    List<Model> getallmodel();

    List<Model> getModelFeatureByUidAndModelName(Integer uid, String modelname);


    String getUrlByalgorithmNameAndUid(Integer uid, String algorithmName);

    List<publicAl> getAlgorithmByAlgorithmName();

    List<String> getFeaByTableName(String tableName);

    String isRepeatModel(String modelname);
}

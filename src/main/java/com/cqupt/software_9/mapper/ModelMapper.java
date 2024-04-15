package com.cqupt.software_9.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.common.ModelDTO;
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


    List<String> getAllModelByPublisherAndUid(String publisher, Integer uid, String diseasename);

    String getModelPathByModelName(String modelname);

    List<ModelDTO> getModel();

    boolean removeModel(String modelname);



    List<String> getDisease();


    Integer getModelNum();



    String getStartTime();

    List<String> getDiagName();


    String getFea(String modelname);

    String getFeaBymodelName(String modelname);
}

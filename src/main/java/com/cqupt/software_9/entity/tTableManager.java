package com.cqupt.software_9.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@TableName(value ="t_table_manager")
//public class tTableManager implements Serializable {
//    @TableId(type = IdType.AUTO)
//    private Integer id;
//    private String  tableName;
//    private String  tableNameCh;
//    private String  fieldName;
//    private String  fieldNameCh;
//    private String  fieldDesc;
//    private String  fieldType;
//    private String  fieldRange;
//    private String  fieldUnit;
//    private String  diseaseType;
//    private String  isDemography;
//    private String  isPhysiological;
//    private String  isSociology;
//    private String  isClinicalRelationship;
//    private String  isMultipleDiseases;
//    private String  isZooInformation;
//    private String  isQuestionnaire;
//    private String  isTimeInformation;
//    private LocalDateTime startTime;
//    private LocalDateTime endTime;
//    private String  timeSpace;
//    private Date    createTime;
//    private LocalDateTime   updateTime;
//    private String  tablePeople;
//    private String  tableOrigin;
//    private String  tableSize;
//    private String  tableStatus;
//    private String  isLifeBehaviorHabit;
//    private String  isClinicalFeature;
//    private String  isSocialEnvironment;
//
//}

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value ="t_table_manager")
public class tTableManager implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String  table_name;
    private String  tabale_name_ch;
    private String  field_name;
    private String  field_name_ch;
    private String  field_desc;
    private String  field_type;
    private String  field_range;
    private String  fileld_unit;
    private String  disease_type;
    private String  is_demography;
    private String  is_physiological;
    private String  is_sociology;
    private String  is_clinical_relationship;
    private String  is_multiple_diseases;
    private String  is_zoo_information;
    private String  is_questionnaire;
    private String  is_time_information;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private String  time_space;
    private Date    create_time;
    private LocalDateTime   update_time;
    private String  table_people;
    private String  table_origin;
    private String  table_size;
    private String  table_status;
    private String  is_life_behavior_habit;
    private String  is_clinical_feature;
    private String  is_social_environment;
    private String  is_label;
    private String userid;
}


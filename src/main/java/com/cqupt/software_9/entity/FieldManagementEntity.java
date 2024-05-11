package com.cqupt.software_9.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO 公共模块新增类

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("field_management")
public class FieldManagementEntity {
    @TableId
    private Integer characterId;
    private String featureName;
    private String chName;
    private Boolean disease_standard;

    // 人口学
//    private Boolean diagnosis;
    private Boolean is_sociology;
    // 生理指标


    // 行为学
//    private Boolean vitalSigns;
    private Boolean is_physiological;
    private String tableName;
    private String unit;
    private Boolean isLabel;
    private Boolean discrete;
    private String range;
    private String disease;
}

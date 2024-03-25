package com.cqupt.software_9.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="data_manager")
@Data
public class DataManager implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String tablename;
    private String diseasename;
    private Integer datanumber;
    private LocalDateTime loadtime;
    private String  operators;
    private String  chinesename;
    private Integer featurenumber;

    @TableField(value = "\"isProjection\"")
    private int isProjection;


    private String affiliationdatabase;
    private String tabletype;
    private String uploadmethod;
    private Integer uid;


}

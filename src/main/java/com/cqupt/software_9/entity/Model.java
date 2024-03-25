package com.cqupt.software_9.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="model")
@Data
public class Model implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer taskid;
    private String modelname;
    private String diseasename;
    private String publisher;
    private String remarks;
    private LocalDateTime createtime;
    private Integer uid;
    private String modeurl;
    private String feature;
}

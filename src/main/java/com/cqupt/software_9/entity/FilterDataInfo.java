package com.cqupt.software_9.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="filter_data_info")
@Data
public class FilterDataInfo {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String uid;
    private String username;
    private String createUser;
    private String cateId;
    private String parentId;
    private Date filterTime;
}

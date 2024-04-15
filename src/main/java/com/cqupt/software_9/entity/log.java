package com.cqupt.software_9.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "log", autoResultMap = true)
public class log {
    @TableId
    private Integer id;
    private String username;
    private Integer uid;
    private String operation;
    private Date optTime;


}

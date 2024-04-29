package com.cqupt.software_9.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="task_manager")
@Data
public class TaskManager {
    @TableId(type = IdType.AUTO)
    private String id;
    private String taskname;
    private String al;
    private String mostacc;
    private String diseasename;
    private String creator;
    private String tablename;
}

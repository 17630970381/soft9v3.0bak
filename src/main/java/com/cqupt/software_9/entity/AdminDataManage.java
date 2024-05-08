package com.cqupt.software_9.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @TableName t_table_manager
 */
@TableName(value ="table_describe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDataManage implements Serializable {
    private String id;

    private String tableId;

    private String tableName;

    private String createUser;

    private String createTime;

    private String classPath;

    private String uid;

    private String tableStatus;

    private float tableSize;

    private String checkApproving;

    private String checkApproved;

    private static final long serialVersionUID = 1L;
}
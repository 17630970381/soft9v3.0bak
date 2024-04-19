package com.cqupt.software_9.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName(value ="user_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLog {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String uid;

    private String username;

    private Date opTime;

    private String opType;
    private Integer role;

    private static final long serialVersionUID = 1L;
}

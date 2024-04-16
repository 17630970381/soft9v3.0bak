package com.cqupt.software_9.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value ="notification",schema = "public")
public class Notification {
    @TableId
    private Integer infoId;

    private Integer uid;

    private String username;

    private Date createTime;

    private String title;

    private String content;

    private Date updateTime;

    private String isDelete;

    private String type;





}

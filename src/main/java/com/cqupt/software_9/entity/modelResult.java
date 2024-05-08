package com.cqupt.software_9.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
@AllArgsConstructor
@Data
@TableName(value ="modelresult")
public class modelResult {
    private Integer id;
    private String modelname;
    private String evaluate;
    private String picture;
    private String pkl;
    private String uid;
    private String al;
    private String tablename;
    private String diseasename;


}

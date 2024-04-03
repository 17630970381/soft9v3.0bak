package com.cqupt.software_9.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelRequestData {
    private String modelname;
    private String evaluate;
    private String picture;
    private String pkl;
    private Integer uid;
    private String al;
    private String tablename;
    private String diseasename;
    private String publisher;
    private String remarks;
    private LocalDateTime createtime;
    private String modeurl;
    private String feature;
}

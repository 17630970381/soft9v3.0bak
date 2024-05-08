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
    private String uid;
    private String al;
    private String tablename;
    private String diseasename;
    private String publisher;
    private String remarks;
    private LocalDateTime createtime;
    private String modeurl;
    private String feature;
    private float  p_calculation_rates;
    private float  b_calculation_rates;
    private String  target;
    private String  mostacc;
    private String  alname;
}

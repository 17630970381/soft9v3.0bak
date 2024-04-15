package com.cqupt.software_9.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class modelAndModelResultDTO {

    private String modelname;
    private String diseasename;
    private String publisher;
    private LocalDateTime createtime;
    private Integer uid;
    private String feature;
    private String evaluate;
    private String pkl;
    private String al;
    private String tablename;

}

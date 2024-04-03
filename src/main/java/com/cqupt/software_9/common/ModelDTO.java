package com.cqupt.software_9.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelDTO {
    private String modelname;
    private String diseasename;
    private String publisher;

    private LocalDateTime createtime;
    private String modeurl;
    private String feature;
}

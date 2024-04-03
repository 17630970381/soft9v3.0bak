package com.cqupt.software_9.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class resultmanage {
    private String modelname;
    private String alname;
    private String samplename;
    private Integer totalsamples;
    private Integer totalfeatures;
    private float accuracy;
    private float precision;
    private float recall;
    private float f1score;

    @Column(columnDefinition = "json")
    private String feavalues;
}

package com.cqupt.software_9.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Detail {
    private String modelname;
    private String tablename;
    private String feature;
    private String importance;
    private String fpercentage;
    private String fvalue;
    private String bpercentage;
    private String bvalue;
}

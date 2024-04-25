package com.cqupt.software_9.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailDTO {
    private String feature;
    private String importance;
    private String fpercentage;
    private String fvalue;
    private String bpercentage;
    private String bvalue;
}

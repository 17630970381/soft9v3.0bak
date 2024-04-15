package com.cqupt.software_9.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureMatch {
    private String featureName;
    private String chName;
    private String unit;
    private boolean isLabel;
    private String range;
}
package com.cqupt.software_9.common;

// TODO 公共模块新增
public enum FeatureType {
//    DIAGNOSIS(0, "diagnosis"),
    DIAGNOSIS(0, "is_sociology"),
    EXAMINE(1, "examine"),
//    PATHOLOGY(2, "pathology"),
    PATHOLOGY(2, "disease_standard"),
//    VITAL_SIGNS(3, "vital_signs");
    VITAL_SIGNS(3, "is_physiological");

    private final int code;
    private final String name;

    FeatureType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}

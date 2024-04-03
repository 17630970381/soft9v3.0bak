package com.cqupt.software_9.common;

public enum OptEnum {
    AND(0, "AND"),
    OR(1, "OR"),
    NOT(2, "NOT");

    private final int code;
    private final String name;

    OptEnum(int code, String name) {
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

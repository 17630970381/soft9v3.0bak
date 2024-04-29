package com.cqupt.software_9.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MergeCondition {
    private String sex;
    private String date1;
    private String date2;
    private String[] nation;
    private String[] maritalStatus;
}

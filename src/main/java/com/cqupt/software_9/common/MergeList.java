package com.cqupt.software_9.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MergeList {
    private String patientId;
    private String name;
    private String sexname;
    private String birthdate;
    private String nationname;
    private String maritalstatusname;
}

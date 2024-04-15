package com.cqupt.software_9.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class history {
    private String path;
    private String[] patientId;
    private String[] fea;
}

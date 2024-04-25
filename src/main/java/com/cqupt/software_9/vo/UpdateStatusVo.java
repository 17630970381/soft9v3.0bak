package com.cqupt.software_9.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusVo {
    private String uid ;
    private Integer role;
    private  String status;
    private double uploadSize;
    private String  userid;
    private double allSize;
}

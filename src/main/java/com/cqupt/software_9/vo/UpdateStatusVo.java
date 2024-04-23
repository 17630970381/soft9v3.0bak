package com.cqupt.software_9.vo;


import lombok.Data;

@Data
public class UpdateStatusVo {
    private String uid ;
    private Integer role;
    private  String status;
    private double uploadSize;
    private String  userid;
}

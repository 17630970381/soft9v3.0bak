package com.cqupt.software_9.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录实体类
 */
@Data
//@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginParam {
    private String username;
    private String password;
    private String code;
}

package com.cqupt.software_9.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


@Data
@Accessors(chain = true)
@TableName(value = "\"user\"", autoResultMap = true)
public class User implements Serializable, UserDetails {

    @TableId
    private String uid;

    private String username;

    private String password;

    private Integer role;

    private Date createTime;

    private Date updateTime;

    private String userStatus;

    private String answer_1;

    private String answer_2;

    private String answer_3;

    private double uploadSize;

    private double allSize;





    private static final long serialVersionUID = 1L;

    // 新增字段
    @TableField(exist = false)
    private String code;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

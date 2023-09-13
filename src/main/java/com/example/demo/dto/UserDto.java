package com.example.demo.dto;

import lombok.Data;

import java.security.Principal;

/**
 * 认证用户
 *
 */
@Data
public class UserDto implements Principal {

    private Long id;
    private String uuid;

    private String username;

    private String password;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 头像地址
     */
    private String avatarUrl;

    private String email;

    private String phone;

    /**
     * 返回:服务器jwt token
     */
    private String token;

    /**
     * 是否是管理员
     */
    private Boolean admin;
    @Override
    public String getName() {
        return uuid;
    }
}
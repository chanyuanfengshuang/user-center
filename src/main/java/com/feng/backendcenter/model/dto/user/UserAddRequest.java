package com.feng.backendcenter.model.dto.user;

import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * 添加用户请求
 */
@Data
public class UserAddRequest implements Serializable {
    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 用户昵称
     */
    private String userNickname;

    /**
     * 用户权限 0 表示管理员 1 表示普通用户
     */
    private Integer userRole;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户电话
     */
    private String userPhone;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 用户地址
     */
    private String userAddress;

    @Serial
    private static final long serialVersionUID = 1L;
}

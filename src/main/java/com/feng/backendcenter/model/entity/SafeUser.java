package com.feng.backendcenter.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author bood
 * @since 2024/10/11 18:39
 */
public class SafeUser implements Serializable {

    /**
     *  用户id
     */
    private Long id;
    /**
     * 账号
     */

    private String userAccount;

    /**
     * 用户昵称
     */
    private String userNickname;

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
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}

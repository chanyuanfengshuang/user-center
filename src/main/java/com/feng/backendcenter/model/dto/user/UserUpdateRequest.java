package com.feng.backendcenter.model.dto.user;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 用户更新请求
 * @author bood
 * @since 2024/10/14 17:04
 */
@Data
public class UserUpdateRequest {

    /**
     *  用户id
     */
    private Long userId;


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

}

package com.feng.backendcenter.constant;

/**
 * 用户常量
 * @author bood
 * @since 2024/10/14 20:52
 */
public interface UserConstant {

    /**
     *  用户登录态
     */
    String USER_LOGIN_STATE = "userLoginState";

    /**
     *  用户权限 0表示管理员，1表示普通用户
     */
    Integer ROLE_ADMIN = 0;
    Integer ROLE_USER = 1;

}

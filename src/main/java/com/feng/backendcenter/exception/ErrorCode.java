package com.feng.backendcenter.exception;

/**
 * 自定义异常状态码
 */
public enum ErrorCode {
    SUCCESS(0,"成功"),
    PARAMERS_ERROR(400,"输入参数错误"),
    USER_PASSWORD_ERROR(401,"用户名或密码错误"),
    AUTHORIZATION_ERROR(403,"权限不足"),
    USER_NOT_FOUND(404,"用户不存在"),
    USER_ALREADY_EXIST(405,"用户已存在"),
    SYSTEM_ERROR(500,"系统异常"),
    USER_NOT_LOGIN(501,"未登录"),
    SYSTEM_BUSY(503,"系统繁忙"),
    ;
    private int code;
    private String description;
    ErrorCode(int code,String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}

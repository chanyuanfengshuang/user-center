package com.feng.backendcenter.exception;

/**
 * @author bood
 * @since 2024/09/29 23:13
 */
public enum ErrorCode {
    SUCCESS(0,"成功"),
    PARAMERS_ERROR(400,"输入参数错误"),
    AUTHORIZATION_ERROR(403,"权限不足"),


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

package com.feng.backendcenter.common;

import com.feng.backendcenter.exception.ErrorCode;

import java.io.Serializable;

/**
 * @author bood
 * @since 2024/10/11 10:45
 */
public class BaseResponse<T> implements Serializable {
    private int code;
    private String message;
    private T data;
    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(int code,T data){
        this(code,null,data);
    }

    public BaseResponse(int code,T data,String message){
        this(code,message,data);
    }

    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(),errorCode.getDescription(),null);
    }



}

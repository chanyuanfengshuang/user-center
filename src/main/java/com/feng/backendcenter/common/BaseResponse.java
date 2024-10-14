package com.feng.backendcenter.common;

import com.feng.backendcenter.exception.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 * @param <T> 返回的数据类型
 */
@Data
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

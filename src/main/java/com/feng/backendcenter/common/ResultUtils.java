package com.feng.backendcenter.common;

import com.feng.backendcenter.exception.ErrorCode;

/**
 *  @description: 响应工具类
 */
public class ResultUtils {

    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse(200,data,"操作成功");
    }
    public static <T> BaseResponse<T> error(ErrorCode errorCode){
        return new BaseResponse(errorCode.getCode(),errorCode.getDescription());
    }

}

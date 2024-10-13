package com.feng.backendcenter.common;

import com.feng.backendcenter.exception.ErrorCode;

/**
 * @author bood
 * @since 2024/10/11 17:03
 */
public class ResultUtils {

    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse(200,data,"操作成功");
    }
    public static <T> BaseResponse<T> error(ErrorCode errorCode){
        return new BaseResponse(errorCode.getCode(),errorCode.getDescription());
    }

}

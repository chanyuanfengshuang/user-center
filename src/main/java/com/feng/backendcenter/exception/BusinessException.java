package com.feng.backendcenter.exception;

/**
 * 自定义异常返回值
 */
public class BusinessException extends RuntimeException{
    /**
     * 错误码
     */
    private final int code;
    /**
     *错误描述
     */
    private String description;

    public BusinessException(int code){
        this.code = code;
    }
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.code =errorCode.getCode();
    }


    public BusinessException(ErrorCode errorCode, String description) {
        super(description);
        this.code = errorCode.getCode();
        this.description = description;
    }
}

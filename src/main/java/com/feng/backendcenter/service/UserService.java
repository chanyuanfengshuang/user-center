package com.feng.backendcenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.backendcenter.common.BaseResponse;
import com.feng.backendcenter.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author 17247
* @description 针对表【user】的数据库操作Service
* @createDate 2024-09-29 00:38:00
*/
public interface UserService extends IService<User> {
    BaseResponse<Long> userLogin(String userAccount, String userPassword, HttpServletRequest httpServletRequest);
    BaseResponse<Long> userRegister(String userAccount,String userPassword,String checkPassword);
}

package com.feng.backendcenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.backendcenter.common.BaseResponse;
import com.feng.backendcenter.model.dto.user.UserAddRequest;
import com.feng.backendcenter.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
* @author 17247
* @description 针对表【user】的数据库操作Service
* @createDate 2024-09-29 00:38:00
*/
public interface UserService extends IService<User> {
    // 登录
    BaseResponse<Long> userLogin(String userAccount, String userPassword, HttpServletRequest httpServletRequest);
    // 注册
    BaseResponse<Long> userRegister(String userAccount,String userPassword,String checkPassword);
    //管理员添加用户
    BaseResponse<Boolean> adminAddUser(UserAddRequest userAddRequest);
    //获取当前登录用户
    BaseResponse<Long> currentLoginUser(HttpServletRequest request);
    //获取用户信息
    BaseResponse<User> getUserInfoById(Long userId);
    //退出登录态
    boolean userLogout(HttpServletRequest request);
}

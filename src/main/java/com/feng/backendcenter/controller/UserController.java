package com.feng.backendcenter.controller;

import com.feng.backendcenter.model.dto.user.UserAddRequest;
import com.feng.backendcenter.model.dto.user.UserLoginRequest;
import com.feng.backendcenter.model.dto.user.UserRegisterRequest;
import com.feng.backendcenter.model.entity.User;
import com.feng.backendcenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bood
 * @since 2024/09/29 22:13
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @PostMapping("/add")
    public void addUser(@RequestBody UserAddRequest userAddRequest){
        User user = new User();
        user.setUserAccount(userAddRequest.getUserAccount());
        user.setUserPassword(userAddRequest.getUserPassword());
        user.setUserNickname(userAddRequest.getUserNickname());
        user.setUserRole(userAddRequest.getUserRole());
        user.setUserProfile(userAddRequest.getUserProfile());
        user.setUserAvatar(userAddRequest.getUserAvatar());
        user.setUserPhone(userAddRequest.getUserPhone());
        user.setUserEmail(userAddRequest.getUserEmail());
        user.setUserAddress(userAddRequest.getUserAddress());
        userService.save(user);
    }

    /**
     *  用户登录
     * @param userLoginRequest 用户登录账号密码请求
     * @param httpServletRequest    httpServletRequest
     * @return  脱敏用户对象
     */

    @PostMapping("/login")
    public int userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest) {
        if (userLoginRequest == null) {
            return -1;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return -1;
        }
        int result = userService.userLogin(userAccount, userPassword, httpServletRequest);
        return result;
    }

    @PostMapping("/register")
    public int userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if(userRegisterRequest == null) {
            return -1;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }
        int result = userService.userRegister(userAccount, userPassword, checkPassword);
        return result;
    }

}

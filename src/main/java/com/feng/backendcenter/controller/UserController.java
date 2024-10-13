package com.feng.backendcenter.controller;

import com.feng.backendcenter.common.BaseResponse;
import com.feng.backendcenter.common.ResultUtils;
import com.feng.backendcenter.exception.BusinessException;
import com.feng.backendcenter.exception.ErrorCode;
import com.feng.backendcenter.model.dto.user.UserAddRequest;
import com.feng.backendcenter.model.dto.user.UserLoginRequest;
import com.feng.backendcenter.model.dto.user.UserRegisterRequest;
import com.feng.backendcenter.model.entity.User;
import com.feng.backendcenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
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

    /**
     *  添加用户
     * @param userAddRequest 用户添加请求
     * @return  添加结果
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> addUser(@RequestBody UserAddRequest userAddRequest){
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
        return ResultUtils.success(true);
    }

    /**
     *  用户登录
     * @param userLoginRequest 用户登录账号密码请求
     * @param httpServletRequest    httpServletRequest
     * @return  脱敏用户对象
     */
    @PostMapping("/login")
    public BaseResponse<Long> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest) throws Exception {
        //1.校验
        if (userLoginRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMERS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return ResultUtils.error(ErrorCode.PARAMERS_ERROR);
        }
        BaseResponse<Long> result = userService.userLogin(userAccount, userPassword, httpServletRequest);
        return result;
    }

    /**
     * 用户注册
     * @param userRegisterRequest 用户注册请求
     * @return 注册是否成功
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if(userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMERS_ERROR,"参数为空");
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();

        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMERS_ERROR,"参数为空");
        }
        BaseResponse<Long> result = userService.userRegister(userAccount, userPassword, checkPassword);
        return result;
    }
}

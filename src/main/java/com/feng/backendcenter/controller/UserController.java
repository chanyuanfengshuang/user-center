package com.feng.backendcenter.controller;

import com.feng.backendcenter.common.BaseResponse;
import com.feng.backendcenter.common.ResultUtils;
import com.feng.backendcenter.exception.BusinessException;
import com.feng.backendcenter.exception.ErrorCode;
import com.feng.backendcenter.model.dto.user.*;
import com.feng.backendcenter.model.entity.User;
import com.feng.backendcenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import static com.feng.backendcenter.constant.UserConstant.ROLE_ADMIN;
import static com.feng.backendcenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 *  用户接口
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;


    /**
     *  用户登录
     * @param userLoginRequest 用户登录账号密码请求
     * @param httpServletRequest    httpServletRequest
     * @return  脱敏用户对象
     */
    @PostMapping("/login")
    public BaseResponse<Long> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest) throws Exception {
        if(userLoginRequest == null){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR,"参数为空");
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
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
        if(userRegisterRequest == null){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR,"参数为空");
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        BaseResponse<Long> result = userService.userRegister(userAccount, userPassword, checkPassword);
        return result;
    }

    /**
     *  获取当前登录用户
     * @param httpServletRequest  httpServletRequest
     * @return 当前登录用户
     */
    @GetMapping("/currentUserLogin")
    public BaseResponse<User> currentUserLogin(HttpServletRequest httpServletRequest) {
        BaseResponse<Long> longBaseResponse = userService.currentLoginUser(httpServletRequest);
        if(longBaseResponse == null){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR,"当前用户未登录");
        }
        User user = userService.getById(longBaseResponse.getData());
        User safeUser = new User();
        safeUser.setUserId(user.getUserId());
        safeUser.setUserAccount(user.getUserAccount());
        safeUser.setUserNickname(user.getUserNickname());
        safeUser.setUserProfile(user.getUserProfile());
        safeUser.setUserAvatar(user.getUserAvatar());
        safeUser.setUserPhone(user.getUserPhone());
        safeUser.setUserEmail(user.getUserEmail());
        safeUser.setUserAddress(user.getUserAddress());
        safeUser.setCreateTime(user.getCreateTime());
        return ResultUtils.success(user);
    }

    /**
     *  通过用户id删除(管理员)
     * @param  userRemoveRequest 用户id
     * @return 是否成功
     */
    @PostMapping("/deleteUserById")
    public BaseResponse<Boolean> deleteUserById(@RequestBody UserRemoveRequest userRemoveRequest,HttpServletRequest request){
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.AUTHORIZATION_ERROR,"权限不足");
        }
        if(userRemoveRequest == null || userRemoveRequest.getId() == null){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR,"用户id不能为空");
        }
        Long id = userRemoveRequest.getId();
        return ResultUtils.success(userService.removeById(id));
    }

    /**
     * 更新用户信息
     * @param userUpdateRequest 用户信息
     * @return 是否成功
     */

    @PostMapping("/updateUser")
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        if(userUpdateRequest == null || userUpdateRequest.getUserId()== null){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR,"用户id不能为空");
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest,user);
        return ResultUtils.success(userService.updateById(user));
    }

    /**
     *  管理员添加用户
     * @param userAddRequest 用户添加请求
     * @return  添加结果
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> adminAddUser(@RequestBody UserAddRequest userAddRequest,HttpServletRequest request){
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.AUTHORIZATION_ERROR,"权限不足");
        }
        if(userAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR,"参数为空");
        }
        BaseResponse<Boolean> booleanBaseResponse = userService.adminAddUser(userAddRequest);
        return ResultUtils.success(booleanBaseResponse.getData());
    }

    /**
     * 通过id查询用户信息（管理员）
     * @param id 用户id
     * @return 用户信息
     */
    @GetMapping("/getUserById")
    public BaseResponse<User> getUserById(Long id,HttpServletRequest request){
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.AUTHORIZATION_ERROR,"权限不足");
        }
        BaseResponse<User> userInfoById = userService.getUserInfoById(id);
        if (userInfoById == null){
            throw new BusinessException(ErrorCode.USER_NOT_FOUND,"用户不存在");
        }
        return ResultUtils.success(userInfoById.getData());
    }

    /**
     * 退出登录
     * @param request 请求
     * @return 退出结果
     */
    @GetMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request){
        if(request == null){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR,"参数为空");
        }
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }


    /**
     * 判断是否为管理员
     * @param request 请求
     * @return 管理员判断结果
     */

    private boolean isAdmin(HttpServletRequest request){
        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) attribute;
        if(user.getUserRole() != ROLE_ADMIN){
            return false;
        }
        return true;
    }

}

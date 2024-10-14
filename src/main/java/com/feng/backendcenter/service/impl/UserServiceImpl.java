package com.feng.backendcenter.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.backendcenter.common.BaseResponse;
import com.feng.backendcenter.common.ResultUtils;
import com.feng.backendcenter.exception.BusinessException;
import com.feng.backendcenter.exception.ErrorCode;
import com.feng.backendcenter.mapper.UserMapper;
import com.feng.backendcenter.model.dto.user.UserAddRequest;
import com.feng.backendcenter.model.entity.User;
import com.feng.backendcenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.feng.backendcenter.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author 17247
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-09-29 00:38:00
*/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;
    //加盐
    private static final String SALT = "feng";


    @Override
    public BaseResponse<Long> userLogin(String userAccount, String userPassword,HttpServletRequest httpServletRequest) {
        //1.参数校验
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR);
        }
        if(userAccount.length() < 4 || userAccount.length() > 8){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR, "用户账号长度不正确");
        }
        //2.查询用户是否存在
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_account",userAccount);
        userPassword = DigestUtil.sha256Hex(userPassword + SALT);
        userQueryWrapper.eq("user_password",userPassword);
        User user = userMapper.selectOne(userQueryWrapper);
        if(user == null){
            log.info("用户不存在");
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "用户不存在");
        }
        //3.用户脱敏
        User safeUser = new User();
        safeUser.setUserId(user.getUserId());
        safeUser.setUserAccount(user.getUserAccount());
        safeUser.setUserRole(user.getUserRole());
        safeUser.setUserNickname(user.getUserNickname());
        safeUser.setUserProfile(user.getUserProfile());
        safeUser.setUserAvatar(user.getUserAvatar());
        safeUser.setUserPhone(user.getUserPhone());
        safeUser.setUserEmail(user.getUserEmail());
        safeUser.setUserAddress(user.getUserAddress());
        safeUser.setCreateTime(user.getCreateTime());

        //4.记录用户登录态
        httpServletRequest.getSession().setAttribute(USER_LOGIN_STATE,safeUser);
        return ResultUtils.success(user.getUserId());
    }

    @Override
    public BaseResponse<Long> userRegister(String userAccount, String userPassword, String checkPassword) {

        //1. 校验账号
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR,"账号密码不能为空");
        }
        if(userAccount.length() < 4 ||userAccount.length() > 8){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR,"账号长度在4到8之间");
        }
        if(!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR,"两次密码不一致");
        }
        if(userPassword.length() < 8 || userPassword.length() > 16){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR,"密码长度在8到16之间");
        }

        //2.指定账号不能包含特殊字符
        String regex = "^[a-zA-Z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userAccount);
        if(!matcher.matches()){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR,"账号只能包含数字和字母");
        }

        //3.账号不能重复
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_Account",userAccount);
        User user = userMapper.selectOne(userQueryWrapper);
        if(user != null) {
            throw new BusinessException(ErrorCode.USER_ALREADY_EXIST, "账号已存在");
        }

        //4.密码加密
        User newUser = new User();
        newUser.setUserAccount(userAccount);
        userPassword = DigestUtil.sha256Hex(userPassword+SALT);
        newUser.setUserPassword(userPassword);
        boolean save = this.save(newUser);
        if(!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"注册失败,数据库异常");
        }
        return ResultUtils.success(newUser.getUserId());
    }

    /**
     *  管理员添加用户
     * @param userAddRequest
     * @return 用户id
     */
    @Override
    public BaseResponse<Boolean> adminAddUser(UserAddRequest userAddRequest) {
        //1.添加用户信息
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
        boolean save = this.save(user);
        if(!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"添加失败,数据库异常");
        }
        return ResultUtils.success(save);
    }

    /**
     *  获取当前登录用户信息
     * @param request 请求
     * @return 用户id
     */
    @Override
    public BaseResponse<Long> currentLoginUser(HttpServletRequest request) {
        //获取当前登录用户信息
        User currentUser = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.USER_NOT_LOGIN,"用户未登录");
        }
        Long userId = currentUser.getUserId();
        //返回用户id
        return ResultUtils.success(userId);
    }

    /**
     *  通过id查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    @Override
    public BaseResponse<User> getUserInfoById(Long userId) {
        //通过用户id查询
        User getUser = this.getById(userId);
        if(getUser == null){
            throw new BusinessException(ErrorCode.USER_NOT_FOUND,"用户不存在");
        }
        return ResultUtils.success(getUser);
    }

    /**
     * 退出登录
     * @param request 请求
     * @return 退出结果
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        if(request.getSession().getAttribute(USER_LOGIN_STATE) == null){
            throw new BusinessException(ErrorCode.USER_NOT_LOGIN,"用户未登录");
        }
        //移除用户登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }


}





package com.feng.backendcenter.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.backendcenter.common.BaseResponse;
import com.feng.backendcenter.common.ResultUtils;
import com.feng.backendcenter.exception.BusinessException;
import com.feng.backendcenter.exception.ErrorCode;
import com.feng.backendcenter.mapper.UserMapper;
import com.feng.backendcenter.model.entity.User;
import com.feng.backendcenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static final String SALT = "feng";

    @Override
    public BaseResponse<Long> userLogin(String userAccount, String userPassword,HttpServletRequest httpServletRequest) {
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR);
        }
        if(userAccount.length() < 4 || userAccount.length() > 8){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR, "用户账号长度不正确");
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_account",userAccount);
        userPassword = DigestUtil.sha256Hex(userPassword + SALT);
        userQueryWrapper.eq("user_password",userPassword);
        User user = userMapper.selectOne(userQueryWrapper);
        if(user == null){
            log.info("用户不存在");
            return ResultUtils.error(ErrorCode.PARAMERS_ERROR);
        }
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
            throw new BusinessException(ErrorCode.PARAMERS_ERROR, "账号已存在");
        }

        //4.密码加密
        User newUser = new User();
        newUser.setUserAccount(userAccount);
        userPassword = DigestUtil.sha256Hex(userPassword+SALT);
        newUser.setUserPassword(userPassword);
        boolean save = this.save(newUser);
        if(!save){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR,"注册失败,数据库异常");
        }
        return ResultUtils.success(newUser.getUserId());
    }

}





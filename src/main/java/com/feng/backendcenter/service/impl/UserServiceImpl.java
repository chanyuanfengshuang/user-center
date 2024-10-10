package com.feng.backendcenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Override
    public int userLogin(String userAccount, String userPassword, HttpServletRequest httpServletRequest) {
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR);
        }
        if(userAccount.length() < 4 && userAccount.length() > 8){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR);
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_account",userAccount);
        userQueryWrapper.eq("user_password",userPassword);
        User user = userMapper.selectOne(userQueryWrapper);
        if(user == null){
            log.info("用户不存在");
            return 0;
        }
        return 1;
    }

    @Override
    public int userRegister(String userAccount, String userPassword, String checkPassword) {
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR,"账号密码不能为空");
        }
        if(userAccount.length() < 4 && userAccount.length() > 8){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR,"账号长度在4到8之间");
        }
        if(!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR,"两次密码不一致");
        }
        if(userPassword.length() < 8 && userPassword.length() > 16){
            throw new BusinessException(ErrorCode.PARAMERS_ERROR,"密码长度在8到16之间");
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_Account",userAccount);
        User user = userMapper.selectOne(userQueryWrapper);
        if(user != null) {
            throw new BusinessException(ErrorCode.PARAMERS_ERROR, "账号已存在");
        }
        User newUser = new User();
        newUser.setUserAccount(userAccount);
        newUser.setUserPassword(userPassword);
        return userMapper.insert(newUser);
    }

}





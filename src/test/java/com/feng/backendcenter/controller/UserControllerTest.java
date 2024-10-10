package com.feng.backendcenter.controller;

import com.feng.backendcenter.model.dto.user.UserAddRequest;
import com.feng.backendcenter.model.dto.user.UserRegisterRequest;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author bood
 * @since 2024/10/10 19:07
 */
@SpringBootTest
class UserControllerTest {

    @Resource
    private UserController userController;

    @Test
    void addUser() {
        String userAccount = "admin";
        String userPassword = "123456";
        String userNickname = "feng";
        String userProfile = "hello";
        String userAvatar = "http://www.baidu.com";
        String userPhone = "123456789";
        String userEmail = "123456789@qq.com";
        String userAddress = "beijing";
        int userRole = 0;
        UserAddRequest user = new UserAddRequest();
        user.setUserAccount(userAccount);
        user.setUserPassword(userPassword);
        user.setUserNickname(userNickname);
        user.setUserRole(userRole);
        user.setUserProfile(userProfile);
        user.setUserAvatar(userAvatar);
        user.setUserPhone(userPhone);
        user.setUserEmail(userEmail);
        user.setUserAddress(userAddress);
        user.setUserAccount(userAccount);
        userController.addUser(user);
    }


    @Test
    void userRegister() {
        String userAccount = "root";
        String userPassword = "123456";
        String checkPassword = "123456";
        UserRegisterRequest user = new UserRegisterRequest();
        user.setUserAccount(userAccount);
        user.setUserPassword(userPassword);
        user.setCheckPassword(checkPassword);
        userController.userRegister(user);
    }
}
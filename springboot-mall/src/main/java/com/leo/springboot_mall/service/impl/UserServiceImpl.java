package com.leo.springboot_mall.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.leo.springboot_mall.service.UserService;

import com.leo.springboot_mall.dao.UserDao;
import com.leo.springboot_mall.dto.UserRegisterRequest;
import com.leo.springboot_mall.model.User;

@Component
public class UserServiceImpl implements UserService{

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        
        // 檢查Email是否已經註冊
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if (user != null) {
            log.warn("Email {} 已被註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        
        // 創建新用戶
        return userDao.createUser(userRegisterRequest);
    }


}

package com.leo.springboot_mall.service;

import com.leo.springboot_mall.dto.UserRegisterRequest;
import com.leo.springboot_mall.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);
}

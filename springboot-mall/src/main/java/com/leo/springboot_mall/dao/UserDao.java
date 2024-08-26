package com.leo.springboot_mall.dao;

import com.leo.springboot_mall.dto.UserRegisterRequest;
import com.leo.springboot_mall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    Integer createUser(UserRegisterRequest userRegisterRequest);

}

package com.agsft.shield.service;

import com.agsft.shield.entitiy.User;

public interface UserService {

    String addUser(User user);

    String loginUser(String userName, String password, String authToken);

    User findUserById(Long userId);
}

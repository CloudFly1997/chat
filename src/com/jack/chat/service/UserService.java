package com.jack.chat.service;

import com.jack.chat.pojo.User;

import java.util.List;

public interface UserService {
    User loginByAccountAndPassword(String account, String password);
    List<User> getFriendsList(String account);
}

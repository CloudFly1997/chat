package com.jack.chat.service;

import com.jack.chat.pojo.User;

public interface UserService {
    User loginByAccountAndPassword(String account, String password);
}

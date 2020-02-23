package com.jack.chat.dao;

import com.jack.chat.pojo.User;

import java.util.List;

public interface UserDao {

    User queryUserByAccountAndPassword(String account, String password) ;
    List<User> getFriendsList(String account);
}

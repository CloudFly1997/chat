package com.jack.chat.dao;

import com.jack.chat.pojo.User;

public interface UserDao {

    public User queryUserByAccountAndPassword(String account, String password) ;

}

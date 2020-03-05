package com.jack.chat.dao;

import com.jack.chat.pojo.User;

import java.util.List;


/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/1 21:22
 */
public interface UserDao {

    /**
     * 验证登录
     * @param account
     * @param password
     * @return
     */
    User queryUserByAccountAndPassword(String account, String password) ;

    /**
     * 根据账号查询好友列表
     * @param account
     * @return
     */
    List<User> getFriendsList(String account);
}

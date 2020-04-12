package com.jack.chat.dao;

import com.jack.chat.pojo.User;


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
     * 查询用户
     * @param account
     * @return
     */
    User queryUserByAccount(String account);

    /**
     * 更新用户
     * @param user
     */
    void updateUser(User user);
}

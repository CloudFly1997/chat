package com.jack.chat.service;

import com.jack.chat.pojo.User;

/**
 * @author jack
 */
public interface UserService {
    /**
     * 验证登录
     * @param account
     * @param password
     * @return
     */
    User loginByAccountAndPassword(String account, String password);

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

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

}

package com.jack.chat.dao;

import com.jack.chat.pojo.Group;

import java.util.List;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/28 21:16
 */

public interface GroupDao {

    /**
     * 创建群
     * @param group
     */
    void create(Group group);

    /**
     * 验证账号是否可用
     * @param account
     */
    void verificationAccount(String account);

    /**
     * 更新群
     * @param group
     */
    void update(Group group);

    /**
     * 查找群
     * @param account
     * @return 群集合
     */
    List<Group> query(String account);
}

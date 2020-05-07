package com.jack.chat.service;

import com.jack.chat.pojo.Group;

import java.util.List;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/28 21:22
 */

public interface GroupService {


    /**
     * 创建群
     *
     * @param group
     */
    void create(Group group);

    /**
     * 验证账号是否可用
     *
     * @param account
     */
    Group verificationAccount(String account);

    /**
     * 更新群
     *
     * @param group
     */
    void update(Group group);

    /**
     * 查找群
     * @param account
     * @return List
     */
    List<Group> query(String account);

    /**
     * 查找群
     * @param id
     * @return
     */
    Group queryById(String id);
}

package com.jack.chat.dao;

import com.jack.chat.pojo.User;

import java.util.List;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/29 15:02
 */

public interface GroupMemberDao {

    /**
     * 添加成员
     *
     * @param userAccount
     * @param groupAccount
     */
    void add(String userAccount, String groupAccount);

    /**
     * 删除成员
     *
     * @param userAccount
     * @param groupAccount
     */
    void delete(String userAccount, String groupAccount);

    /**
     * 修改成员备注
     */
    void update();

    /**
     * 查询群成员
     * @param groupAccount
     * @return List
     */
    List< User> query(String groupAccount);
}

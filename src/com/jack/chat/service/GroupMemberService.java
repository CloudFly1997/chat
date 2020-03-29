package com.jack.chat.service;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/29 15:02
 */

public interface GroupMemberService {

    /**
     * 添加成员
     * @param userAccount
     * @param groupAccount
     */
    void add(String userAccount, String groupAccount) ;

    /**
     * 删除成员
     * @param userAccount
     * @param groupAccount
     */
    void delete(String userAccount, String groupAccount);

    /**
     * 修改成员备注
     */
    void update();

    /**
     * 查询成员
     */
    void query();
}

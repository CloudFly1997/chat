package com.jack.chat.dao;

import com.jack.chat.pojo.User;

import java.util.List;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/6 21:55
 */

public interface FriendDao {
    /**
     * 获取好友列表
     *
     * @param account
     * @return
     */
    List<User> getFriendsList(String account);

    /**
     * 删除好友
     * @param userAccount
     * @param friendAccount
     */
    void deleteFriend(String userAccount, String friendAccount) ;

    /**
     * 修改好友备注
     * @param userAccount
     * @param friendAccount
     * @param newRemark
     */
    void changeRemark(String userAccount, String friendAccount, String newRemark);

    /**
     * 查看好友个人信息
     *
     * @param account
     * @return
     */
    User viewProfile(String account);

    /**
     * 加好友
     * @param account1
     * @param account2
     */
    void addFriend(String account1,String account2);


}

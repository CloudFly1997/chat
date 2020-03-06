package com.jack.chat.service;

import com.jack.chat.pojo.User;

import java.util.List;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/6 21:40
 */

public interface FriendService {
    /**
     * 获取好友列表
     * @param account
     * @return
     */
    List<User> getFriendsList(String account);

    /**
     * 删除好友
     * @param userAccount
     * @param friendAccount
     */
    void deleteFriend(String userAccount, String friendAccount);

    /**
     * 修改好友备注
     * @param user_account
     * @param friend_account
     * @param newRemark
     */
    void changeRemark(String user_account, String friend_account, String newRemark);

    /**
     * 查看好友个人信息
     * @param account
     * @return
     */
    User viewProfile(String account);

    /**
     * 加好友
     * @param account
     */
    void addFriend(String account);


}

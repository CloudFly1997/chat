package com.jack.chat.service.imp;

import com.jack.chat.dao.FriendDao;
import com.jack.chat.dao.imp.FriendDaoImpl;
import com.jack.chat.pojo.User;
import com.jack.chat.service.FriendService;

import java.util.List;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/6 21:44
 */

public class FriendServiceImpl implements FriendService {


    private static FriendService friendService = null;

    private FriendServiceImpl(){

    }

    public static FriendService getInstance(){
        if (friendService == null) {
            friendService = new FriendServiceImpl();
        }
        return friendService;
    }

    private  FriendDao friendDao = FriendDaoImpl.getInstance();

    @Override
    public List<User> getFriendsList(String account) {
        return friendDao.getFriendsList(account);
    }

    @Override
    public void deleteFriend(String userAccount, String friendAccount) {
        friendDao.deleteFriend(userAccount, friendAccount);
    }


    @Override
    public void changeRemark(String user_account, String friend_account, String newRemark) {
        friendDao.changeRemark(user_account,friend_account,newRemark);
    }

    @Override
    public User viewProfile(String account) {
        return friendDao.viewProfile(account);
    }

    @Override
    public void addFriend(String account1,String account2) {
        friendDao.addFriend(account1,account2);
    }
}

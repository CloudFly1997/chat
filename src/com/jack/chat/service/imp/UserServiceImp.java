package com.jack.chat.service.imp;

import com.jack.chat.dao.UserDao;
import com.jack.chat.dao.imp.UserDaoImp;
import com.jack.chat.pojo.User;
import com.jack.chat.service.UserService;

import java.util.List;

public class UserServiceImp implements UserService {
    UserDao userDao = new UserDaoImp();
    @Override
    public User loginByAccountAndPassword(String account, String password) {
        return userDao.queryUserByAccountAndPassword(account,password);
    }

    @Override
    public List<User> getFriendsList(String account) {
        return userDao.getFriendsList(account);
    }
}

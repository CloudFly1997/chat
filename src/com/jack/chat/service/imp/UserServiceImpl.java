package com.jack.chat.service.imp;

import com.jack.chat.dao.UserDao;
import com.jack.chat.dao.imp.UserDaoImp;
import com.jack.chat.pojo.User;
import com.jack.chat.service.UserService;

/**
 * @author jack
 */
public class UserServiceImpl implements UserService {

    private static UserService userService = null;

    private UserServiceImpl(){

    }

    public static UserService getInstance(){
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }
    UserDao userDao = UserDaoImp.getInstance();
    @Override
    public User loginByAccountAndPassword(String account, String password) {
        return userDao.queryUserByAccountAndPassword(account,password);
    }


    @Override
    public User queryUserByAccount(String account){
        return userDao.queryUserByAccount(account);
    }
}

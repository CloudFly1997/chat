package com.jack.chat.service.imp;

import com.jack.chat.dao.GroupDao;
import com.jack.chat.dao.imp.GroupDaoImpl;
import com.jack.chat.pojo.Group;
import com.jack.chat.service.GroupService;

import java.util.List;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/28 21:42
 */

public class GroupServiceImpl implements GroupService {

    private static GroupServiceImpl groupServiceImp = new GroupServiceImpl();

    private GroupServiceImpl() {

    }

    public static GroupService getInstance() {
        return groupServiceImp;
    }

    GroupDao groupDao = GroupDaoImpl.getInstance();

    @Override
    public void create(Group group) {
        groupDao.create(group);
    }

    @Override
    public void verificationAccount(String account) {

    }

    @Override
    public void update(Group group) {

    }

    @Override
    public List<Group> query(String account) {
        return groupDao.query(account);
    }

    @Override
    public Group queryById(String id) {
        return groupDao.queryById(id);
    }
}

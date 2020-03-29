package com.jack.chat.service.imp;

import com.jack.chat.dao.GroupMemberDao;
import com.jack.chat.dao.imp.GroupMemberDaoImpl;
import com.jack.chat.service.GroupMemberService;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/29 16:59
 */

public class GroupMemberServiceImpl implements GroupMemberService {

    private static GroupMemberServiceImpl groupMemberServiceImpl = new GroupMemberServiceImpl();

    private GroupMemberServiceImpl(){

    }

    public static GroupMemberService getInstance() {
        return groupMemberServiceImpl;
    }

    GroupMemberDao groupMemberDao = GroupMemberDaoImpl.getInstance();

    @Override
    public void add(String userAccount, String groupAccount) {
        groupMemberDao.add(userAccount,groupAccount);
    }

    @Override
    public void delete(String userAccount, String groupAccount) {

    }

    @Override
    public void update() {

    }

    @Override
    public void query() {

    }
}

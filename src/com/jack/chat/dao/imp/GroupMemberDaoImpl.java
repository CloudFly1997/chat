package com.jack.chat.dao.imp;

import com.jack.chat.dao.GroupMemberDao;
import com.jack.chat.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/29 15:06
 */

public class GroupMemberDaoImpl implements GroupMemberDao {

    private static GroupMemberDaoImpl groupMemberDaoImpl = new GroupMemberDaoImpl();

    private GroupMemberDaoImpl() {

    }

    public static GroupMemberDao getInstance() {
        return groupMemberDaoImpl;
    }

    public static final String ADD_GROUP_MEMBER_SQL = "INSERT INTO chat.`group_member` VALUES (?,?,?);";

    @Override
    public void add(String userAccount, String groupAccount) {
        Connection connection = DbUtil.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(ADD_GROUP_MEMBER_SQL);
            preparedStatement.setString(1,groupAccount);
            preparedStatement.setString(2,userAccount);
            preparedStatement.setString(3,userAccount);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(connection,null,preparedStatement);
        }
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

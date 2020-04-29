package com.jack.chat.dao.imp;

import com.jack.chat.dao.GroupMemberDao;
import com.jack.chat.dao.packaging.ResultSetToObject;
import com.jack.chat.exception.DbException;
import com.jack.chat.pojo.User;
import com.jack.chat.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public static final String QUERY_GROUP_MEMBER_SQL = "SELECT * FROM chat.`user` WHERE user_id IN (SELECT user_id " +
            "FROM group_member WHERE group_id = ?)";

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
            throw new DbException(e);
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
    public List<User> query(String groupAccount) {
        Connection connection = DbUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(QUERY_GROUP_MEMBER_SQL);
            preparedStatement.setString(1, groupAccount);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User user = ResultSetToObject.rsToUserObject(rs);
                users.add(user);
            }
        }catch (SQLException e) {
            throw new DbException(e);
        }finally {
            DbUtil.close(connection,rs,preparedStatement);
        }
        return users;
    }


}

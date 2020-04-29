package com.jack.chat.dao.imp;

import com.jack.chat.dao.GroupDao;
import com.jack.chat.dao.packaging.ResultSetToObject;
import com.jack.chat.exception.DbException;
import com.jack.chat.pojo.Group;
import com.jack.chat.pojo.User;
import com.jack.chat.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/28 21:23
 */

public class GroupDaoImpl implements GroupDao {

    private static GroupDaoImpl groupDaoImpl = new GroupDaoImpl();

    private GroupDaoImpl() {

    }

    public static GroupDao getInstance() {
        return groupDaoImpl;
    }

    public static final String CREATE_GROUP_SQL = "INSERT INTO chat.`group` " +
            "(group_id,group_name,group_holder,group_introduce,group_avatar) " +
            "VALUES (?,?,?,?,?);";

    public static final String QUERY_GROUP_SQL = "SELECT * FROM chat.`group` " +
            "WHERE group_id IN " +
            "(SELECT group_id FROM group_member WHERE user_id = ?) ";

    public static final String QUERY_GROUP_BY_ID_SQL = "SELECT * FROM chat.`group` WHERE group_id = ?";

    @Override
    public void create(Group group) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbUtil.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_GROUP_SQL);
            preparedStatement.setString(1, group.getGroupAccount());
            preparedStatement.setString(2, group.getGroupName());
            preparedStatement.setString(3, group.getGroupIntroduce());
            preparedStatement.setString(4, group.getGroupHolder());
            preparedStatement.setBinaryStream(5, group.getInputStream());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DbException(e);
        } finally {
            DbUtil.close(connection, null, preparedStatement);
        }
    }

    @Override
    public void verificationAccount(String account) {

    }

    @Override
    public void update(Group group) {

    }

    @Override
    public List<Group> query(String account) {
        List<Group> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbUtil.getConnection();
            preparedStatement = connection.prepareStatement(QUERY_GROUP_SQL);
            preparedStatement.setString(1, account);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Group group = ResultSetToObject.rsToGroupObject(resultSet);
                List<User> memberList = GroupMemberDaoImpl.getInstance().query(group.getGroupAccount());
                Map<String, User> memberMap = new HashMap<>();
                for (User user :
                        memberList) {
                    memberMap.put(user.getAccount(), user);
                }
                group.setMembers(memberMap);
                list.add(group);
            }
        } catch (SQLException e) {
            throw new DbException(e);
        } finally {
            DbUtil.close(connection, null, preparedStatement);
        }
        return list;
    }

    @Override
    public Group queryById(String id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Group group = null;
        try {
            connection = DbUtil.getConnection();
            preparedStatement = connection.prepareStatement(QUERY_GROUP_BY_ID_SQL);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                group = ResultSetToObject.rsToGroupObject(resultSet);
                List<User> memberList = GroupMemberDaoImpl.getInstance().query(group.getGroupAccount());
                Map<String, User> memberMap = new HashMap<>();
                for (User user :
                        memberList) {
                    memberMap.put(user.getAccount(), user);
                }
                group.setMembers(memberMap);

            }
            return group;
        } catch (SQLException e) {
            throw new DbException(e);
        } finally {
            DbUtil.close(connection, null, preparedStatement);
        }
    }
}

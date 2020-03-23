package com.jack.chat.dao.imp;

import com.jack.chat.dao.FriendDao;
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
 * @date 2020/3/6 22:09
 */

public class FriendDaoImpl implements FriendDao {

    private static FriendDao friendDao = null;

    private FriendDaoImpl(){

    }
    public static FriendDao getInstance(){
        if (friendDao == null) {
            friendDao = new FriendDaoImpl();
        }
        return friendDao;
    }
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    String getFriendsSql = "SELECT user.*, f.friend_remark FROM chat.`user`,(SELECT friend_id,friend_remark FROM friend WHERE user_id = ?) f WHERE user_id = f.friend_id";

    String deleteSql = "DELETE FROM friend WHERE user_id = ? AND friend_id = ?";

    String changeRemarkSql = "UPDATE friend SET friend_remark = ? WHERE user_id = ? AND friend_id = ?";

    String addFriendSql = "INSERT INTO friend (user_id,friend_id) VALUES (?,?),(?,?)";

    @Override
    public List<User> getFriendsList(String account) {
        List<User> friendsList = new ArrayList<User>();
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(getFriendsSql);
            ps.setString(1, account);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = ResultSetToObject.rsToUserObject(rs);
                String friendRemark = rs.getString("friend_remark");
                user.setFriend_remark(friendRemark);
                friendsList.add(user);
            }
            return friendsList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException();
        } finally {
            DbUtil.close(conn, rs, ps);
        }
    }

    @Override
    public void deleteFriend(String userAccount, String friendAccount) {
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(deleteSql);
            ps.setString(1,userAccount);
            ps.setString(2,friendAccount);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeRemark(String userAccount, String friendAccount, String newRemark) {
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(changeRemarkSql);
            ps.setString(1,newRemark);
            ps.setString(2,userAccount);
            ps.setString(3,friendAccount);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public User viewProfile(String account) {
        return UserDaoImp.getInstance().queryUserByAccount(account);
    }

    @Override
    public void addFriend(String account1,String account2) {
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(addFriendSql);
            ps.setString(1,account1);
            ps.setString(2,account2);
            ps.setString(3,account2);
            ps.setString(4,account1);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

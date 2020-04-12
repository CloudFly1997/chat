package com.jack.chat.dao.imp;

import com.jack.chat.dao.UserDao;
import com.jack.chat.dao.packaging.ResultSetToObject;
import com.jack.chat.exception.DbException;
import com.jack.chat.pojo.User;
import com.jack.chat.util.DbUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jack
 */
public class UserDaoImp implements UserDao {

    private static UserDao userDao = null;

    private UserDaoImp(){

    }
    public static UserDao getInstance(){
        if (userDao == null) {
            userDao = new UserDaoImp();
        }
        return userDao;
    }
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    String loginSql = "SELECT * FROM user WHERE user_id = ? AND password =?";

    String querySql = "SELECT * FROM user WHERE user_id = ?";

    String updateSql = "UPDATE chat.`user` SET nick_name = ?,birthday = ?,gender = ?,email = ?,phone_number = ?," +
            "address = ?,signature = ?,avatar = ? WHERE user_id = ?";

    @Override
    public User queryUserByAccountAndPassword(String account, String password) {
        User user = null;
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(loginSql);
            ps.setString(1, account);
            ps.setString(2, password);
            rs = ps.executeQuery();
            while (rs.next()) {
                user = ResultSetToObject.rsToUserObject(rs);
            }
            return user;
        } catch (SQLException e) {
            throw new DbException(e);
        } finally {
            DbUtil.close(conn, rs, ps);
        }
    }

    @Override
    public User queryUserByAccount(String account) {
        User user = null;
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(querySql);
            ps.setString(1, account);
            rs = ps.executeQuery();
            while (rs.next()) {
                user = ResultSetToObject.rsToUserObject(rs);
            }
            return user;
        } catch (SQLException e) {
            throw new DbException(e);
        } finally {
            DbUtil.close(conn, rs, ps);
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(updateSql);
            ps.setString(1,user.getNickName());
            ps.setString(2,user.getBirthday());
            ps.setString(3,user.getGender());
            ps.setString(4,user.getEmail());
            ps.setString(5,user.getPhoneNumber());
            ps.setString(6,user.getAddress());
            ps.setString(7,user.getSignature());
            ps.setBinaryStream(8,user.getAvatar(),user.getAvatar().available());
            ps.setString(9,user.getAccount());
            ps.executeUpdate();
        } catch (SQLException | IOException e) {
            throw new DbException(e);
        } finally {
            DbUtil.close(conn, null, ps);
        }
    }


}

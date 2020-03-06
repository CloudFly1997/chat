package com.jack.chat.dao.imp;

import com.jack.chat.dao.UserDao;
import com.jack.chat.dao.packaging.ResultSetToObject;
import com.jack.chat.exception.DbException;
import com.jack.chat.pojo.User;
import com.jack.chat.util.DbUtil;

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
            e.printStackTrace();
            throw new DbException();
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
            e.printStackTrace();
            throw new DbException();
        } finally {
            DbUtil.close(conn, rs, ps);
        }
    }


}

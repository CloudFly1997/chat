package com.jack.chat.dao.imp;

import com.jack.chat.exception.DbException;
import com.jack.chat.dao.UserDao;
import com.jack.chat.pojo.User;
import com.jack.chat.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImp implements UserDao {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public User queryUserByAccountAndPassword(String account, String password) {
        User user = null;
        try {
            String sql = "SELECT * FROM user WHERE account = ? AND password =?";
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1,account);
            ps.setString(2,password);
            rs = ps.executeQuery();
            while (rs.next()) {
                String accountFromDb = rs.getString("account");
                String passwordFromDb = rs.getString("password");
                String nickNameFromDb = rs.getString("nick_name");
                String birthdayFromDb = rs.getString("birthday");
                String addressFromDb = rs.getString("address");
                Integer phoneNumberFromDb = rs.getInt("phone_number");
                String emailFromDb = rs.getString("email");
                String signature = rs.getString("signature");
                user = new User(accountFromDb,passwordFromDb,nickNameFromDb,birthdayFromDb,addressFromDb,phoneNumberFromDb,emailFromDb,signature);
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException();
        } finally {
            DbUtil.close(conn,rs,ps);
        }
    }
}

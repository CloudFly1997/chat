package com.jack.chat.dao.imp;

import com.jack.chat.exception.DbException;
import com.jack.chat.dao.UserDao;
import com.jack.chat.pojo.User;
import com.jack.chat.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImp implements UserDao {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    String loginSql = "SELECT * FROM user WHERE account = ? AND password =?";
    String getFriendsSql = "SELECT * FROM user WHERE account IN(SELECT friend_account FROM friend WHERE account = ?)";
    @Override
    public User queryUserByAccountAndPassword(String account, String password) {
        User user = null;
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(loginSql);
            ps.setString(1,account);
            ps.setString(2,password);
            rs = ps.executeQuery();
            while (rs.next()) {
                String accountFromDb = rs.getString("account");
                //String passwordFromDb = rs.getString("password");
                String nickNameFromDb = rs.getString("nick_name");
                String genderFromDb = rs.getString("gender");
                String birthdayFromDb = rs.getString("birthday");
                String addressFromDb = rs.getString("address");
                Integer phoneNumberFromDb = rs.getInt("phone_number");
                String emailFromDb = rs.getString("email");
                String signature = rs.getString("signature");
                user = new User(accountFromDb,nickNameFromDb,genderFromDb,birthdayFromDb,addressFromDb,phoneNumberFromDb,emailFromDb,signature);
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException();
        } finally {
            DbUtil.close(conn,rs,ps);
        }
    }

    public List<User> getFriendsList(String account) {
        List<User> friendsList = new ArrayList<User>();
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(getFriendsSql);
            ps.setString(1,account);
            rs = ps.executeQuery();
            while (rs.next()) {
                String accountFromDb = rs.getString("account");
                //String passwordFromDb = rs.getString("password");
                String nickNameFromDb = rs.getString("nick_name");
                String genderFromDb = rs.getString("gender");
                String birthdayFromDb = rs.getString("birthday");
                String addressFromDb = rs.getString("address");
                Integer phoneNumberFromDb = rs.getInt("phone_number");
                String emailFromDb = rs.getString("email");
                String signature = rs.getString("signature");
                User user = new User(accountFromDb,nickNameFromDb,genderFromDb,birthdayFromDb,addressFromDb,phoneNumberFromDb,emailFromDb,signature);
                friendsList.add(user);
            }
            return  friendsList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException();
        } finally {
            DbUtil.close(conn,rs,ps);
        }
    }
}

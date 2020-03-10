package com.jack.chat.dao.packaging;

import com.jack.chat.pojo.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/7 0:11
 */
public class ResultSetToObject {
    public static User rsToUserObject(ResultSet resultSet) throws SQLException {
        String accountFromDb = resultSet.getString("user_id");
        String nickNameFromDb = resultSet.getString("nick_name");
        String genderFromDb = resultSet.getString("gender");
        String birthdayFromDb = resultSet.getString("birthday");
        String addressFromDb = resultSet.getString("address");
        String phoneNumberFromDb = resultSet.getString("phone_number");
        String emailFromDb = resultSet.getString("email");
        String signature = resultSet.getString("signature");
        User user = new User(accountFromDb, nickNameFromDb, genderFromDb, birthdayFromDb, addressFromDb, phoneNumberFromDb, emailFromDb, signature);
        return user;
    }
}

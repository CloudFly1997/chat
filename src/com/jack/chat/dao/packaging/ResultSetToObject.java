package com.jack.chat.dao.packaging;

import com.jack.chat.pojo.Group;
import com.jack.chat.pojo.User;
import com.jack.transfer.Message;

import java.io.InputStream;
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

    public static Group rsToGroupObject(ResultSet resultSet) throws SQLException {
        String groupAccount = resultSet.getString("group_id");
        String groupName = resultSet.getString("group_name");
        String groupIntroduce = resultSet.getString("group_introduce");
        String groupHolder = resultSet.getString("group_holder");
        InputStream groupAvatar = resultSet.getBinaryStream("group_avatar");
        Group group = new Group(groupAccount, groupName, groupIntroduce, groupAvatar, groupHolder);
        return group;
    }

    public static Message rsToMessageObject(ResultSet resultSet) throws SQLException {
        String messageDate = resultSet.getString("message_date");
        String type = resultSet.getString("message_type");
        String fromUser = resultSet.getString("from_user");
        String toUser = resultSet.getString("to_user");
        String content = resultSet.getString("content");
        boolean isRead = resultSet.getBoolean("is_read");
        Message message = new Message(fromUser, toUser, content, messageDate, type, isRead);
        return message;
    }

}

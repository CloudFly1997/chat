package com.jack.chat.dao.imp;

import com.jack.chat.dao.MessageDao;
import com.jack.chat.pojo.Message;
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
 * @date 2020/3/5 21:19
 */

public class MessageDaoImp implements MessageDao {

    Connection conn = DbUtil.getConnection();
    String getHistoryMessageSql = "SELECT * FROM message WHERE to_user = ? AND from_user = ? AND is_read = 1 " +
            "ORDER BY message_date DESC LIMIT 0, 50";
    String getUnReadMessageSql = "SELECT * FROM message WHERE to_user = ? AND from_user = ? AND is_read = 0";

    @Override
    public List<Message> QueryUnReadMessage(User from, User to) {
        return queryMessageByToAndFrom(getUnReadMessageSql, from, to);
    }

    @Override
    public List<Message> QueryHistoryMessage(User from, User to) {
        return queryMessageByToAndFrom(getHistoryMessageSql, from, to);
    }

    private List<Message> queryMessageByToAndFrom(String sql, User from, User to) {
        PreparedStatement psForMessage = null;
        ResultSet rsForMessage = null;
        List<Message> list = new ArrayList<Message>();
        try {
            psForMessage = conn.prepareStatement(sql);
            psForMessage.setString(1, to.getAccount());
            psForMessage.setString(2, from.getAccount());
            rsForMessage = psForMessage.executeQuery();
            while (rsForMessage.next()) {
                String messageDate = rsForMessage.getString("message_date");
                String type = rsForMessage.getString("message_type");
                String fromUser = rsForMessage.getString("from_user");
                String toUser = rsForMessage.getString("to_user");
                String content = rsForMessage.getString("content");
                Message message = new Message(fromUser, toUser, content, messageDate, type);
                list.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

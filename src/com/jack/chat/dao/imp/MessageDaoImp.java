package com.jack.chat.dao.imp;

import com.jack.chat.dao.MessageDao;
import com.jack.chat.pojo.User;
import com.jack.chat.util.DbUtil;
import com.jack.transfer.Message;

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

    String getHistoryMessageSql = "SELECT * FROM message WHERE (to_user = ? AND from_user = ?) OR (to_user = " +
            "? AND from_user = ?) AND is_read = 1 ORDER BY message_date  LIMIT 0, 50";
    String getUnReadMessageSql = "SELECT * FROM message WHERE to_user = ? AND from_user = ? AND is_read = 0";
    String makeReadSql = "UPDATE message SET is_read = 1 WHERE (to_user = ? AND from_user = ?) OR (to_user = ? AND " +
            "from_user = ?)";

    @Override
    public List<Message> QueryUnReadMessage(User from, User to) {
        Connection conn = DbUtil.getConnection();
        PreparedStatement psForMessage = null;
        ResultSet rsForMessage = null;
        List<Message> list = new ArrayList<Message>();
        try {
            psForMessage = conn.prepareStatement(getUnReadMessageSql);
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
        } finally {
            DbUtil.close(conn, rsForMessage, psForMessage);
        }
        return list;
    }

    @Override
    public List<Message> QueryHistoryMessage(User from, User to) {
        Connection conn = DbUtil.getConnection();
        PreparedStatement psForMessage = null;
        ResultSet rsForMessage = null;
        List<Message> list = new ArrayList<Message>();
        try {
            psForMessage = conn.prepareStatement(getHistoryMessageSql);
            psForMessage.setString(1, to.getAccount());
            psForMessage.setString(2, from.getAccount());
            psForMessage.setString(3, from.getAccount());
            psForMessage.setString(4, to.getAccount());
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
        } finally {
            DbUtil.close(conn, rsForMessage, psForMessage);
        }
        return list;
    }

    @Override
    public void makeRead(User from, User to) throws SQLException {
        Connection conn = DbUtil.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(makeReadSql);
        preparedStatement.setString(1, from.getAccount());
        preparedStatement.setString(2, to.getAccount());
        preparedStatement.setString(3, to.getAccount());
        preparedStatement.setString(4, from.getAccount());
        preparedStatement.executeUpdate();
        DbUtil.close(conn, null, null);
    }


}

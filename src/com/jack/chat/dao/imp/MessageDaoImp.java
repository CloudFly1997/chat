package com.jack.chat.dao.imp;

import com.jack.chat.dao.MessageDao;
import com.jack.chat.dao.packaging.ResultSetToObject;
import com.jack.chat.exception.DbException;
import com.jack.chat.pojo.CommonIndividual;
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

    String getFriendHistoryMessageSql = "SELECT * FROM message WHERE (to_user = ? AND from_user = ?) OR (to_user = " +
            "? AND from_user = ?) ORDER BY message_date  LIMIT 0, 50";
    String getGroupHistoryMessageSql = "SELECT * FROM message WHERE to_user = ? ORDER BY message_date  LIMIT 0, 50";
    String getUnReadMessageSql = "SELECT * FROM message WHERE to_user = ? AND from_user = ? AND is_read = 0";
    String makeReadSql = "UPDATE message SET is_read = 1 WHERE to_user = ? AND from_user = ?";

    @Override
    public List<Message> queryUnReadMessage(CommonIndividual from, CommonIndividual to) {
        Connection conn = DbUtil.getConnection();
        PreparedStatement psForMessage = null;
        ResultSet rsForMessage = null;
        List<Message> list = new ArrayList<Message>();
        try {
            psForMessage = conn.prepareStatement(getUnReadMessageSql);
            psForMessage.setString(1, to.getId());
            psForMessage.setString(2, from.getId());
            rsForMessage = psForMessage.executeQuery();
            while (rsForMessage.next()) {
                Message message = ResultSetToObject.rsToMessageObject(rsForMessage);
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
    public List<Message> queryFriendHistoryMessage(CommonIndividual from, CommonIndividual to) {
        Connection conn = DbUtil.getConnection();
        PreparedStatement psForMessage = null;
        ResultSet rsForMessage = null;
        List<Message> list = new ArrayList<Message>();
        try {
            psForMessage = conn.prepareStatement(getFriendHistoryMessageSql);
            psForMessage.setString(1, to.getId());
            psForMessage.setString(2, from.getId());
            psForMessage.setString(3, from.getId());
            psForMessage.setString(4, to.getId());
            rsForMessage = psForMessage.executeQuery();
            while (rsForMessage.next()) {
                Message message = ResultSetToObject.rsToMessageObject(rsForMessage);
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
    public List<Message> queryGroupHistoryMessage(CommonIndividual group) {
        Connection conn = DbUtil.getConnection();
        PreparedStatement psForMessage = null;
        ResultSet rsForMessage = null;
        List<Message> list = new ArrayList<Message>();
        try {
            psForMessage = conn.prepareStatement(getGroupHistoryMessageSql);
            psForMessage.setString(1, group.getId());
            rsForMessage = psForMessage.executeQuery();
            while (rsForMessage.next()) {
                Message message = ResultSetToObject.rsToMessageObject(rsForMessage);
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
    public void makeRead(CommonIndividual from, CommonIndividual to)  {
        Connection conn = DbUtil.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(makeReadSql);
            preparedStatement.setString(1, from.getId());
            preparedStatement.setString(2, to.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e);
        } finally {
            DbUtil.close(conn, null, preparedStatement);
        }
    }


}

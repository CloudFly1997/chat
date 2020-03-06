package com.jack.chat.service.imp;

import com.jack.chat.dao.MessageDao;
import com.jack.chat.dao.imp.MessageDaoImp;
import com.jack.chat.pojo.Message;
import com.jack.chat.pojo.User;
import com.jack.chat.service.MessageService;

import java.util.List;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/5 21:54
 */

public class MessageServiceImpl implements MessageService {
    MessageDao messageDao = new MessageDaoImp();

    @Override
    public List<Message> QueryUnReadMessage(User from, User to) {
        return messageDao.QueryUnReadMessage(from, to);
    }

    @Override
    public List<Message> QueryHistoryMessage(User from, User to) {
        return messageDao.QueryHistoryMessage(from, to);
    }
}

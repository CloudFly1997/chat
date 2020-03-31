package com.jack.chat.service.imp;

import com.jack.chat.dao.MessageDao;
import com.jack.chat.dao.imp.MessageDaoImp;
import com.jack.chat.pojo.CommonIndividual;
import com.jack.chat.service.MessageService;
import com.jack.transfer.Message;

import java.util.List;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/5 21:54
 */

public class MessageServiceImpl implements MessageService {
    MessageDao messageDao = new MessageDaoImp();

    @Override
    public List<Message> queryUnReadMessage(CommonIndividual from, CommonIndividual to) {
        return messageDao.queryUnReadMessage(from, to);
    }

    @Override
    public List<Message> queryHistoryMessage(CommonIndividual from, CommonIndividual to) {
        return messageDao.queryFriendHistoryMessage(from, to);
    }

    @Override
    public List<Message> queryGroupHistoryMessage(CommonIndividual group) {
        return messageDao.queryGroupHistoryMessage(group);
    }

    @Override
    public void makeRead(CommonIndividual from, CommonIndividual to) {
        messageDao.makeRead(from, to);
    }
}

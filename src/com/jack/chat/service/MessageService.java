package com.jack.chat.service;

import com.jack.chat.pojo.User;
import com.jack.transfer.Message;

import java.util.List;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/5 21:53
 */

public interface MessageService {
    /**
     * 查询未读消息
     * @param from 消息发送者
     * @param to   消息接收者
     * @return
     */
    public List<Message> QueryUnReadMessage(User from, User to);

    /**
     * 查询历史消息，默认为100条
     * @param from 消息发送者
     * @param to   消息接收者
     * @return
     */
    public List<Message> QueryHistoryMessage(User from, User to);
}

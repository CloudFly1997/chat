package com.jack.transfer;

import com.jack.chat.util.TimeUtil;

import java.io.Serializable;

/**
 * @author jack
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fromUser;
    private String toUser;
    private String messageContent;
    private String dateTime;
    private String type;
    private boolean isRead;

    /**
     * 不带时间的消息构造器
     *
     * @param fromUser
     * @param toUser
     * @param messageContent
     * @param type
     */
    public Message(String fromUser, String toUser, String messageContent, String type) {
        super();
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.messageContent = messageContent;
        this.dateTime = TimeUtil.getNowTime();
        this.type = type;
    }

    public Message(String fromUser, String toUser, String messageContent, String dateTime, String type, boolean isRead) {
        super();
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.messageContent = messageContent;
        this.dateTime = dateTime;
        this.type = type;
        this.isRead = isRead;
    }

    public Message(String fromUser, String toUser, String type) {
        super();
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.dateTime = TimeUtil.getNowTime();
        this.type = type;
    }

    public Message() {

    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return "Message{" +
                "fromUser='" + fromUser + '\'' +
                ", toUser='" + toUser + '\'' +
                ", messageContent='" + messageContent + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

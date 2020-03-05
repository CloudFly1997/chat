package com.jack.chat.util;

import com.jack.chat.pojo.Message;

/**
 * @author jack
 */
public class MessageHandle {
    private static String splitCode = "@AA@";

    public static String afterHandleMessage(String type, String from, String to, String date, String content) {
        String result = type + splitCode + from + splitCode + to + splitCode + date + splitCode + content;
        return result;
    }

    public static String messageShow(String message) {
        String from = message.split(splitCode)[1];
        String date = message.split(splitCode)[2];
        String content = message.split(splitCode)[3];
        message = date + "  " + from + ":\n" + content + "\n\n";
        return content;
    }


    public static Message getMessageObject(String message) {
        String type = message.split(splitCode)[0];
        String from = message.split(splitCode)[1];
        String to = message.split(splitCode)[2];
        String date = message.split(splitCode)[3];
        String content = message.split(splitCode)[4];
        Message messageObj = new Message(type, from, to, content, date);
        return messageObj;
    }

    public static String getFrom(String originMessage) {
        return originMessage.split(splitCode)[1];
    }

    public static String getContent(String originMessage) {
        return originMessage.split(splitCode)[4];
    }
}

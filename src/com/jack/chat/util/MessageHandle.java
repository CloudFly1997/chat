package com.jack.chat.util;

/**
 * @author jack
 */
public class MessageHandle {
    private static final String SPLIT_CODE = "@AA@";

    public static String afterHandleMessage(String type, String from, String to, String date, String content) {
        String result = type + SPLIT_CODE + from + SPLIT_CODE + to + SPLIT_CODE + date + SPLIT_CODE + content;
        return result;
    }

    public static String messageShow(String message) {
        return message.split(SPLIT_CODE)[3];
    }


/*    public static Message getMessageObject(String message) {
        String type = message.split(SPLIT_CODE)[0];
        String from = message.split(SPLIT_CODE)[1];
        String to = message.split(SPLIT_CODE)[2];
        String date = message.split(SPLIT_CODE)[3];
        String content = message.split(SPLIT_CODE)[4];
        Message messageObj = new Message(type, from, to, content, date);
        return messageObj;
    }*/

    public static String getFrom(String originMessage) {
        return originMessage.split(SPLIT_CODE)[1];
    }

    public static String getContent(String originMessage) {
        return originMessage.split(SPLIT_CODE)[4];
    }

    public static String getMessageType(String message) {
        return message.split(SPLIT_CODE)[0];
    }
}

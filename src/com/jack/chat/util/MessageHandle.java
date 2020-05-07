package com.jack.chat.util;


public class MessageHandle {
    public static String getFileNameFromMessageContent(String content) {
        return  content.substring(content.indexOf(Command.SPLIT_CODE) + 1,
                content.lastIndexOf(Command.SPLIT_CODE));
    }

    public static String getFileSizeFromMessageContent(String content) {
        return content.substring(content.lastIndexOf(Command.SPLIT_CODE) + 1);
    }

    public static String getNeedDownLoadFileNameFromMessageContent(String content) {
       return  content.substring(0, content.lastIndexOf(Command.SPLIT_CODE));
    }
}

package com.jack.chat.util;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/21 11:31
 */

public class CommandHandle {
    public static final String SPLIT_CODE = "@AA@";
    public static final String ADD_FRIEND = "[ADD_FRIEND]";
    public static final String AGREE_ADD_FRIEND = "[AGREE_ADD_FRIEND]";
    public static String addFriend(String from, String to) {
        String command =  ADD_FRIEND + SPLIT_CODE + from + SPLIT_CODE + to;
        return command;
    }
    public static String getWhoAddMe(String originMessage) {
        return originMessage.split(SPLIT_CODE)[1];
    }

    public static String agreeAddFriend(String from,String to) {
        String command =  AGREE_ADD_FRIEND + SPLIT_CODE + from + SPLIT_CODE + to;
        return command;
    }
}

package com.jack.chat.common;

import com.jack.chat.component.FriendPane;

import com.jack.chat.pojo.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jack
 */
public class FriendPaneHolder {
    private static FriendPaneHolder friendPaneHolder = new FriendPaneHolder();
    private static Map<String, FriendPane> friendPaneMap = new HashMap<>();

    private User currentChatUser;

    private FriendPaneHolder() {

    }

    public static FriendPaneHolder getInstance() {
        return friendPaneHolder;
    }

    public void addFriendPane(String account, FriendPane friendPane) {
        friendPaneMap.put(account, friendPane);
    }

    public FriendPane getFriendPane(String account) {
        return friendPaneMap.get(account);
    }

    public void setCurrentChatUser(User currentChatUser) {
        this.currentChatUser = currentChatUser;
    }
    public User getCurrentChatUser() {
        return currentChatUser;
    }

    public boolean contains(String account) {
        return friendPaneMap.containsKey(account);
    }

    public void remove(String account) {
        friendPaneMap.remove(account);
    }
}

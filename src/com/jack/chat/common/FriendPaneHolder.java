package com.jack.chat.common;

import com.jack.chat.component.FriendPane;

import java.util.HashMap;
import java.util.Map;

public class FriendPaneHolder {
    private static FriendPaneHolder friendPaneHolder = new FriendPaneHolder();
    private static Map<String, FriendPane> friendPaneMap = new HashMap<>();

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
}

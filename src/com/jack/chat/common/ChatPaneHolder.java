package com.jack.chat.common;

import com.jack.chat.component.ChatPane;
import com.jack.chat.pojo.User;

import java.util.HashMap;
import java.util.Map;

public class ChatPaneHolder {
    private static ChatPaneHolder chatPaneHolder = new ChatPaneHolder();
    private static Map<User,ChatPane> chatPaneMap = new HashMap<User,ChatPane>();
    private static ChatPane currentChatPane;
    private ChatPaneHolder() {

    }
    public static ChatPaneHolder getInstance() {
        return chatPaneHolder;
    }
    public void addChatPane(User user, ChatPane chatPane) {
        chatPaneMap.put(user,chatPane);
    }
    public void removeChatPane(User user){
        chatPaneMap.remove(user);
    }
    public ChatPane getChatPane(User user) {
        return chatPaneMap.get(user);
    }

    public ChatPane getCurrentChatPane() {
        return currentChatPane;
    }

    public void setCurrentChatPane(User user) {
        ChatPaneHolder.currentChatPane = chatPaneMap.get(user);
    }

    public boolean contain(User user) {
        return chatPaneMap.containsKey(user);
    }
}

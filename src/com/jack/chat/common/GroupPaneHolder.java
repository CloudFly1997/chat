package com.jack.chat.common;

import com.jack.chat.component.GroupPane;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jack
 */
public class GroupPaneHolder {
    private static GroupPaneHolder GroupPaneHolder = new GroupPaneHolder();
    private static Map<String, GroupPane> groupPaneMap = new HashMap<>();



    private GroupPaneHolder() {

    }

    public static GroupPaneHolder getInstance() {
        return GroupPaneHolder;
    }

    public void addGroupPane(String account, GroupPane groupPane) {
        groupPaneMap.put(account, groupPane);
    }

    public GroupPane getGroupPane(String account) {
        return groupPaneMap.get(account);
    }


    public boolean contains(String account) {
        return groupPaneMap.containsKey(account);
    }

    public void remove(String account) {
        groupPaneMap.remove(account);
    }
}

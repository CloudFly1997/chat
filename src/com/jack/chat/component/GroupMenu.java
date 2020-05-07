package com.jack.chat.component;

import com.jack.chat.pojo.Group;
import com.jack.chat.service.FriendService;
import com.jack.chat.service.imp.FriendServiceImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/6 21:09
 */

public class GroupMenu extends ContextMenu {

    Group group;
    GroupPane groupPane;
    FriendService friendService = FriendServiceImpl.getInstance();

    public GroupMenu(GroupPane groupPane) {
        this.groupPane = groupPane;
        this.group = groupPane.getGroup();
        MenuItem viewProfile = new MenuItem("查看群资料");
        viewProfile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        MenuItem settingRemark = new MenuItem("设置备注");
        settingRemark.setOnAction(event -> {

        });

        MenuItem quitGroup = new MenuItem("退出群");

        quitGroup.setOnAction(event -> {

        });

        MenuItem reportFriend = new MenuItem("举报好友");

        getItems().add(viewProfile);
        getItems().add(settingRemark);
        getItems().add(quitGroup);
        getItems().add(reportFriend);
        this.setAutoHide(true);
    }
}

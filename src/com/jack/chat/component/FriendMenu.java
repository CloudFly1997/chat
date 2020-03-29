package com.jack.chat.component;

import com.jack.chat.common.FriendPaneHolder;
import com.jack.chat.common.MainWindowHolder;
import com.jack.chat.common.Session;
import com.jack.chat.pojo.User;
import com.jack.chat.service.FriendService;
import com.jack.chat.service.imp.FriendServiceImpl;
import com.jack.chat.util.Command;
import com.jack.transfer.Message;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.io.IOException;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/6 21:09
 */

public class FriendMenu extends ContextMenu {

    User user;
    FriendPane friendPane;
    FriendService friendService = FriendServiceImpl.getInstance();

    public FriendMenu(FriendPane friendPane) {
        this.friendPane = friendPane;
        this.user = friendPane.getUser();
        MenuItem viewProfile = new MenuItem("查看好友资料");
        viewProfile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new FriendProfilePane(user).show();
            }
        });
        MenuItem settingRemark = new MenuItem("设置备注");
        settingRemark.setOnAction(event -> {

        });

        MenuItem deleteFriend = new MenuItem("删除好友");

        deleteFriend.setOnAction(event -> {
            friendService.deleteFriend(Session.getInstance().getUser().getAccount(), user.getAccount());
            friendService.deleteFriend(user.getAccount(), Session.getInstance().getUser().getAccount());
            MainWindowHolder.getInstance().getMainWindow().friendListBox.getChildren().remove(friendPane);
            FriendPaneHolder.getInstance().remove(user.getAccount());
            try {
                Session.getInstance().getOos().writeObject(new Message(Session.getInstance().getUser().getAccount(),
                        user.getAccount(), Command.DELETE_FRIEND));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        MenuItem reportFriend = new MenuItem("举报好友");

        getItems().add(viewProfile);
        getItems().add(settingRemark);
        getItems().add(deleteFriend);
        getItems().add(reportFriend);
        this.setAutoHide(true);
    }
}

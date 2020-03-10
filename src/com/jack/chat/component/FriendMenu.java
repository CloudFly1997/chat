package com.jack.chat.component;

import com.jack.chat.common.Session;
import com.jack.chat.pojo.User;
import com.jack.chat.service.FriendService;
import com.jack.chat.service.imp.FriendServiceImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/6 21:09
 */

public class FriendMenu extends ContextMenu {

    User user;
    FriendService friendService = FriendServiceImpl.getInstance();

    public FriendMenu(User user) {
        this.user = user;
        MenuItem viewProfile = new MenuItem("查看好友资料");
        viewProfile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                FriendProfilePane friendProfilePane = new FriendProfilePane(user);
                Scene scene = new Scene(friendProfilePane);
                stage.setScene(scene);
                stage.show();
                System.out.println(friendService.viewProfile(user.getAccount()));
            }
        });
        MenuItem settingRemark = new MenuItem("设置备注");
        settingRemark.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        MenuItem deleteFriend = new MenuItem("删除好友");
        deleteFriend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                friendService.deleteFriend(Session.getInstance().getUser().getAccount(),user.getAccount());
            }
        });

        MenuItem reportFriend = new MenuItem("举报好友");

        getItems().add(viewProfile);
        getItems().add(settingRemark);
        getItems().add(deleteFriend);
        getItems().add(reportFriend);
    }
}

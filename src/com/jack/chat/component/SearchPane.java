package com.jack.chat.component;

import com.jack.chat.common.FriendPaneHolder;
import com.jack.chat.common.Session;
import com.jack.chat.pojo.User;
import com.jack.chat.service.imp.UserServiceImpl;
import com.jack.chat.util.AvatarLoad;
import com.jack.chat.util.Command;
import com.jack.transfer.Message;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/14 23:45
 */

public class SearchPane extends GridPane {
    public TextField searchField, nickName, account, address;
    public Button addFriend;
    public ImageView avatar;

    public SearchPane() {
        init();
    }

    public void init() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/searchPane.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        Stage stage = new Stage();
        Scene scene = new Scene(this);
        stage.setScene(scene);
        stage.show();
    }

    public void search() {
        String chat = "发起会话";
        String add = "添加好友";
        String searchString = searchField.getText();
        User user = UserServiceImpl.getInstance().queryUserByAccount(searchString);
        if (user != null) {
            FriendPaneHolder friendPaneHolder = FriendPaneHolder.getInstance();
            AvatarLoad.loadAddFriendAvatar(avatar, user.getAccount());
            nickName.setText(user.getNickName());
            account.setText(user.getAccount());
            address.setText(user.getAddress());
            addFriend.setStyle("visibility: visible");
            if (friendPaneHolder.contains(user.getAccount())) {
                addFriend.setText("发起会话");
            }
            addFriend.setOnMouseClicked(event -> {
                if (chat.equals(addFriend.getText())) {
                    System.out.println("发起会话");
                } else if (add.equals(addFriend.getText())) {
                    try {
                        Message message = new Message(Session.getInstance().getUser().getAccount(),
                                user.getAccount(), Command.ADD_FRIEND);
                        Session.getInstance().getOos().writeObject(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    addFriend.setText("以发送");
                }

            });

        }
    }
}

package com.jack.chat.component;

import com.jack.chat.common.GroupPaneHolder;
import com.jack.chat.common.Session;
import com.jack.chat.dao.imp.GroupDaoImpl;
import com.jack.chat.pojo.Group;
import com.jack.chat.util.AvatarUtil;
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

public class SearchGroupPane extends GridPane {
    public TextField searchField, nickName, account, address;
    public Button addFriend;
    public ImageView avatar;

    public SearchGroupPane() {
        init();
    }

    public void init() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/searchPane.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
            addFriend.setText("申请加群");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        Stage stage = new Stage();
        Scene scene = new Scene(this);
        stage.setScene(scene);
        stage.setTitle("搜索群");
        stage.show();
    }

    public void search() {
        String chat = "发送消息";
        String add = "申请加群";
        String searchString = searchField.getText();
        Group group = GroupDaoImpl.getInstance().queryById(searchString);
        if (group != null) {

            GroupPaneHolder groupPaneHolder = GroupPaneHolder.getInstance();
            AvatarUtil.loadAvatar(avatar, group);
            nickName.setText(group.getGroupName());
            account.setText(group.getGroupAccount());
            address.setText(group.getGroupIntroduce());
            addFriend.setStyle("visibility: visible");
            if (groupPaneHolder.contains(group.getGroupAccount())) {
                addFriend.setText(chat);
            }
            addFriend.setOnMouseClicked(event -> {
                if (chat.equals(addFriend.getText())) {

                } else if (add.equals(addFriend.getText())) {
                    try {
                        Message message = new Message(Session.getInstance().getUser().getAccount(),
                                group.getGroupHolder(), group.getGroupAccount(), Command.APPLY_JOIN_GROUP);
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

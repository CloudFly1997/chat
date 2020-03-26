package com.jack.chat.component;

import com.jack.chat.common.FriendPaneHolder;
import com.jack.chat.common.MainWindowHolder;
import com.jack.chat.common.Session;
import com.jack.chat.service.FriendService;
import com.jack.chat.service.imp.FriendServiceImpl;
import com.jack.chat.service.imp.UserServiceImpl;
import com.jack.chat.util.Command;
import com.jack.transfer.Message;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/23 0:35
 */

public class AddFriendDialog extends AnchorPane {
    public TextArea notification;
    public Button agreeButton, refuseButton;
    String account;

    public AddFriendDialog(String account) {
         this.account = account;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/addFriendDialog.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.prefWidthProperty().bind(MainWindowHolder.getInstance().getMainWindow().messageAreaScrollPane.prefWidthProperty());
        notification.setText(account + "请求添加您为好友！");
    }

    public void agree() throws IOException {
        String user = Session.getInstance().getUser().getAccount();
        FriendService friendService = FriendServiceImpl.getInstance();
        friendService.addFriend(user, account);
        FriendPane newFriendPane = new FriendPane(UserServiceImpl.getInstance().queryUserByAccount(account));
        FriendPaneHolder.getInstance().addFriendPane(account,newFriendPane);
        MainWindowHolder.getInstance().getMainWindow().friendList.getChildren().add(1, newFriendPane);
        Session.getInstance().getOos().writeObject(new Message(user, account, Command.AGREE_ADD_FRIEND));
        agreeButton.setText("已同意");
        agreeButton.setDisable(true);
        refuseButton.setDisable(true);
    }

    public void refuse() {
        refuseButton.setText("已拒绝");
        agreeButton.setDisable(true);
        refuseButton.setDisable(true);
    }
}

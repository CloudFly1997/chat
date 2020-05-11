package com.jack.chat.component;

import com.jack.chat.common.FriendPaneHolder;
import com.jack.chat.common.GroupPaneHolder;
import com.jack.chat.common.MainWindowHolder;
import com.jack.chat.common.Session;
import com.jack.chat.pojo.User;
import com.jack.chat.service.FriendService;
import com.jack.chat.service.imp.FriendServiceImpl;
import com.jack.chat.service.imp.GroupMemberServiceImpl;
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

public class NotifyDialog extends AnchorPane {
    public TextArea notification;
    public Button agreeButton, refuseButton;
    Message message;

    public NotifyDialog(Message message) {
        this.message = message;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/notifyDialog.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
            agreeButton.setText("同意");
            refuseButton.setText("拒绝");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.prefWidthProperty().bind(MainWindowHolder.getInstance().getMainWindow().messageAreaScrollPane.prefWidthProperty());
        if (Command.APPLY_JOIN_GROUP.equals(message.getType())) {
            String groupName =
                    GroupPaneHolder.getInstance().getGroupPane(message.getMessageContent()).getGroup().getGroupName();
            notification.setText(message.getFromUser() + "申请加入" + groupName + "群聊！");
        } else if (Command.ADD_FRIEND.equals(message.getType())) {
            notification.setText(message.getFromUser()  + "请求添加您为好友！");
        }
    }

    public void agree() throws IOException {
        if (Command.ADD_FRIEND.equals(message.getType())) {
            String user = Session.getInstance().getUser().getAccount();
            FriendService friendService = FriendServiceImpl.getInstance();
            friendService.addFriend(user, message.getFromUser());
            User newUser = UserServiceImpl.getInstance().queryUserByAccount(message.getFromUser());
            FriendPane newFriendPane = new FriendPane(newUser);
            FriendPaneHolder.getInstance().addFriendPane(message.getFromUser(), newFriendPane);
            MainWindowHolder.getInstance().getMainWindow().friendListBox.getChildren().add(0, newFriendPane);
            Session.getInstance().getOos().writeObject(new Message(user, message.getFromUser(), Command.AGREE_ADD_FRIEND));
        } else if (Command.APPLY_JOIN_GROUP.equals(message.getType())){
            GroupMemberServiceImpl.getInstance().add(message.getFromUser(),message.getMessageContent());
            User user = UserServiceImpl.getInstance().queryUserByAccount(message.getFromUser());
            GroupPaneHolder.getInstance().getGroupPane(message.getMessageContent()).getGroup().getMembers().put(user.getAccount(),user);
            Session.getInstance().getOos().writeObject(new Message(message.getMessageContent(), message.getFromUser(),
                    Command.AGREE_JOIN_GROUP));
        }
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

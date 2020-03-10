package com.jack.chat.component;


import com.jack.chat.common.FriendPaneHolder;
import com.jack.chat.common.Session;
import com.jack.chat.pojo.Message;
import com.jack.chat.pojo.User;
import com.jack.chat.service.MessageService;
import com.jack.chat.service.imp.MessageServiceImpl;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.List;


/**
 * @author jack
 */
public class FriendPane extends HBox implements Runnable{
    public ImageView friendAvatar;
    private User user;
    private VBox chatRecordBox;
    public Label nickName, signature, unReadMessageCountLabel;
    private int unReadMessageCount;
    MessageService messageService = new MessageServiceImpl();


    public FriendPane(User user)  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/friendPane.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        friendAvatar.setImage(new Image("img/下载.jpg"));
        this.user = user;
        if (user.getFriend_remark() == null) {
            this.nickName.setText(user.getNickName());
        } else {
            this.nickName.setText(user.getFriend_remark());
        }
        this.signature.setText(user.getSignature());
        chatRecordBox = new VBox(5);
        unReadMessageCountLabel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (Integer.valueOf(newValue) == 0) {
                    unReadMessageCountLabel.setManaged(false);
                    unReadMessageCountLabel.setVisible(false);
                } else {
                    unReadMessageCountLabel.setManaged(true);
                    unReadMessageCountLabel.setVisible(true);
                }
            }
        });
        Platform.runLater(this);
    }

    public VBox getChatRecordBox() {
        this.unReadMessageCount = 0;
        unReadMessageCountLabel.setText(String.valueOf(unReadMessageCount));
        return chatRecordBox;
    }

    public void receiveMessage(TextFlow messageCarrier) {
        if (FriendPaneHolder.getInstance().getCurrentChatUser() != user) {
            unReadMessageCountAdd();
        }
        chatRecordBox.getChildren().add(messageCarrier);
    }

    public User getUser() {
        return user;
    }

    public void setUnReadMessageCountLabel(int unReadMessageCount) {
        this.unReadMessageCountLabel.setText(String.valueOf(unReadMessageCount));
    }

    /**
     * 登录之后拉取历史消息
     */
    public void pullHistoryMessage() {
        List<Message> historyMessageList = messageService.QueryHistoryMessage(user, Session.getInstance().getUser());
        for (Message message :
                historyMessageList) {
            chatRecordBox.getChildren().add(new MessageCarrier(message.getMessageContent()));
        }
    }

    /**
     * 登录之后拉取未读消息
     */
    public void pullUnReadMessage() {
        List<Message> historyMessageList = messageService.QueryUnReadMessage(user, Session.getInstance().getUser());
        for (Message message :
                historyMessageList) {
            chatRecordBox.getChildren().add(new MessageCarrier(message.getMessageContent()));
            unReadMessageCountAdd();
        }
    }

    public void unReadMessageCountAdd() {
        unReadMessageCount++;//未读消息加1
        unReadMessageCountLabel.setText(String.valueOf(unReadMessageCount));
    }

    @Override
    public void run() {
        pullHistoryMessage();
        pullUnReadMessage();
    }
}

package com.jack.chat.component;


import com.jack.chat.common.MainWindowHolder;
import com.jack.chat.common.Session;
import com.jack.chat.controller.MainWindow;
import com.jack.chat.pojo.User;
import com.jack.chat.service.MessageService;
import com.jack.chat.service.imp.MessageServiceImpl;
import com.jack.chat.util.AvatarUtil;
import com.jack.chat.util.Command;
import com.jack.chat.util.PlaySound;
import com.jack.transfer.Message;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


/**
 * @author jack
 */
public class FriendPane extends HBox {
    public ImageView friendAvatar;
    private User user;
    private VBox chatRecordBox;
    public Label nickName, signature, unReadMessageCountLabel;
    private int unReadMessageCount;
    MessageService messageService = new MessageServiceImpl();
    MainWindow mainWindow = MainWindowHolder.getInstance().getMainWindow();
    Session session = Session.getInstance();

    public FriendPane(User user) {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/friendPane.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AvatarUtil.loadAvatar(friendAvatar, user);
        this.user = user;
        if (user.getFriend_remark() == null) {
            this.nickName.setText(user.getNickName()==null?user.getAccount():user.getNickName());
        } else {
            this.nickName.setText(user.getFriend_remark());
        }
        this.signature.setText(user.getSignature());
        chatRecordBox = new VBox(5);
        this.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                //双击事件
            }
            if (event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                try {
                    chat(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (event.getButton().name().equals(MouseButton.SECONDARY.name())) {
                new FriendMenu(this).show(this, Side.RIGHT, 0, 0);
            }
        });
        unReadMessageCountLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Integer.valueOf(newValue) == 0) {
                unReadMessageCountLabel.setManaged(false);
                unReadMessageCountLabel.setVisible(false);
            } else {
                unReadMessageCountLabel.setManaged(true);
                unReadMessageCountLabel.setVisible(true);
            }
        });

        pullHistoryMessage();
    }

    public VBox getChatRecordBox() {
        this.unReadMessageCount = 0;
        unReadMessageCountLabel.setText(String.valueOf(unReadMessageCount));
        return chatRecordBox;
    }

    public void receiveMessage(Node node) {
        if (!user.equals(session.getCurrentChatWith())) {
            unReadMessageCountAdd();
        }
        chatRecordBox.getChildren().add(node);
        PlaySound.playSoundWhenReceiveMessage();
    }

    public User getUser() {
        return user;
    }

    public void setUnReadMessageCountLabel(int unReadMessageCount) {
        this.unReadMessageCountLabel.setText(String.valueOf(unReadMessageCount));
    }

    /**
     * 登录之后拉取消息
     */
    public void pullHistoryMessage() {
        Task<List<Message>> pullHistoryMessage = new Task<List<Message>>() {

            @Override
            protected void updateValue(List<Message> value) {
                super.updateValue(value);
                for (Message message :
                        value) {

                    if (message.getFromUser().equals(Session.getInstance().getUser().getAccount())) {
                        chatRecordBox.getChildren().add(new MessageCarrier(true, message));
                    } else {
                        chatRecordBox.getChildren().add(new MessageCarrier(message));
                        if (!message.isRead()) {
                            unReadMessageCountAdd();
                        }
                    }
                }
            }

            @Override
            protected List<Message> call() throws Exception {
                return messageService.queryHistoryMessage(user, Session.getInstance().getUser());
            }
        };
        //new Thread(pullHistoryMessage).start();
    }

    /**
     * 登录之后拉取未读消息
     */
    public void pullUnReadMessage() {
        List<Message> historyMessageList = messageService.queryUnReadMessage(user, Session.getInstance().getUser());
        for (Message message :
                historyMessageList) {
            chatRecordBox.getChildren().add(new MessageCarrier(message));
            unReadMessageCountAdd();
        }
    }

    public void unReadMessageCountAdd() {
        unReadMessageCount++;//未读消息加1
        unReadMessageCountLabel.setText(String.valueOf(unReadMessageCount));
    }


    public void chat(User user) throws SQLException {
        if (!user.getAccount().equals(session.getCurrentChatWith())) {
            mainWindow.chatWith.setText(user.getNickName());
            session.setCurrentChatWith(user.getAccount());
            session.setCurrentChatWithType(Command.FRIEND);
            mainWindow.chatWith.setText(user.getNickName());
            this.getChatRecordBox().heightProperty().addListener((observable, oldValue, newValue) -> mainWindow.messageAreaScrollPane.setVvalue(1));
            mainWindow.messageAreaScrollPane.setContent(this.getChatRecordBox());
        }
        new MessageServiceImpl().makeRead(Session.getInstance().getUser(), user);
        setUnReadMessageCountLabel(0);
    }
}

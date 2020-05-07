package com.jack.chat.component;

import com.jack.chat.common.MainWindowHolder;
import com.jack.chat.common.Session;
import com.jack.chat.controller.MainWindow;
import com.jack.chat.pojo.Group;
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
import java.util.List;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/28 22:11
 */

public class GroupPane extends HBox {
    public ImageView groupAvatar;
    private Group group;
    private VBox chatRecordBox;
    public Label groupName, unReadMessageCountLabel;
    private int unReadMessageCount;
    MessageService messageService = new MessageServiceImpl();
    MainWindow mainWindow = MainWindowHolder.getInstance().getMainWindow();
    Session session = Session.getInstance();

    public GroupPane(Group group) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/groupPane.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.group = group;
        this.groupName.setText(group.getGroupName());
        AvatarUtil.loadAvatar(groupAvatar, group);
        chatRecordBox = new VBox(5);
        this.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                //双击事件
            }
            if (event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                    chat(group);
            } else if (event.getButton().name().equals(MouseButton.SECONDARY.name())) {
                new GroupMenu(this).show(this, Side.RIGHT, 0, 0);
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

    }

    public VBox getChatRecordBox() {
        this.unReadMessageCount = 0;
        unReadMessageCountLabel.setText(String.valueOf(unReadMessageCount));
        return chatRecordBox;
    }

    public void receiveMessage(Node node) {
        if (!group.getGroupAccount().equals(session.getCurrentChatWith())) {
            unReadMessageCountAdd();
        }
        chatRecordBox.getChildren().add(node);
        PlaySound.playSoundWhenReceiveMessage();
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setUnReadMessageCountLabel(int unReadMessageCount) {
        this.unReadMessageCountLabel.setText(String.valueOf(unReadMessageCount));
    }

    /**
     * 登录之后拉取历史消息
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
                    }
                }
            }

            @Override
            protected List<Message> call() throws Exception {
                return messageService.queryGroupHistoryMessage(group);
            }
        };
        new Thread(pullHistoryMessage).start();
    }

    /**
     * 登录之后拉取未读消息
     */
    public void pullUnReadMessage() {
/*      List<Message> historyMessageList = messageService.QueryUnReadMessage(user, Session.getInstance().getUser());
        for (Message message :
                historyMessageList) {
            chatRecordBox.getChildren().add(new MessageCarrier(message));
            unReadMessageCountAdd();
        }*/
    }

    public void unReadMessageCountAdd() {
        unReadMessageCount++;//未读消息加1
        unReadMessageCountLabel.setText(String.valueOf(unReadMessageCount));
    }


    public void chat(Group group) {
        if (!group.getGroupAccount().equals(session.getCurrentChatWith())) {
            session.setCurrentChatWith(group.getGroupAccount());
            session.setCurrentChatWithType(Command.GROUP);
            mainWindow.chatWith.setText(group.getGroupName());
            this.getChatRecordBox().heightProperty().addListener((observable, oldValue, newValue) -> mainWindow.messageAreaScrollPane.setVvalue(1));
            mainWindow.messageAreaScrollPane.setContent(this.getChatRecordBox());
        }
        new MessageServiceImpl().makeRead(Session.getInstance().getUser(), group);
        this.setUnReadMessageCountLabel(0);
    }
}

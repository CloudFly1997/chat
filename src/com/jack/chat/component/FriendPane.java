package com.jack.chat.component;


import com.jack.chat.pojo.User;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.Queue;


/**
 * @author jack
 */
public class FriendPane extends HBox {
    public HBox root;
    public ImageView friendAvatar;
    private Queue<String> messageQueen;
    private User user;
    private VBox chatRecordBox;
    public VBox nickNameAndSignature;
    public Label nickName, signature, unReadMessageCountLabel;
    private int unReadMessageCount;


    public FriendPane(User user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/friendPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        EventHandler<MouseEvent> eventEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2 && event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                    //双击事件
                }
                if (event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                    System.out.println("toChat");
                } else if (event.getButton().name().equals(MouseButton.SECONDARY.name())) {
                    System.out.println("viewProfile");
                }
            }
        };
        fxmlLoader.load();
        friendAvatar.setImage(new Image("img/下载.jpg"));
        this.user = user;
        this.nickName.setText(user.getNickName());
        this.signature.setText(user.getSignature());
        chatRecordBox = new VBox(5);
    }

    public VBox getChatRecordBox() {
        return chatRecordBox;
    }

    public void receiveMessage(TextFlow messageCarrier) {
        System.out.println("i am here");
        unReadMessageCount++;//未读消息加1
        unReadMessageCountLabel.setText(String.valueOf(unReadMessageCount));
        chatRecordBox.getChildren().add(messageCarrier);
    }

    public User getUser() {
        return user;
    }
}

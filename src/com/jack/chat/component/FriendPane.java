package com.jack.chat.component;


import com.jack.chat.pojo.User;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.LinkedList;
import java.util.Queue;


/**
 * @author jack
 */
public class FriendPane extends Pane {
    private Queue<String> messageQueen;
    private User user;
    private VBox chatRecordBox;
    public FriendPane(User user) {
        this.user = user;
        init();
    }
    private void init(){
        HBox hBox = new HBox(1);
        hBox.setPrefSize(310,100);
        hBox.setStyle("-fx-background-color:#ABD8ED");
        hBox.setOnMouseEntered(event -> hBox.setStyle("-fx-background-color: #039ED3"));
        hBox.setOnMouseExited(event -> hBox.setStyle("-fx-background-color:#ABD8ED"));
        ImageView imageView = new ImageView();
        imageView.getStyleClass().add("friendAvatar");
        imageView.setFitHeight(hBox.getPrefHeight() - 20);
        imageView.setFitWidth(hBox.getPrefHeight() - 20);
        imageView.setImage(new Image("img/下载.jpg"));
        hBox.setPadding(new Insets(10));
        hBox.getChildren().add(imageView);
        VBox vBox = new VBox();
        Label nickName = new Label(user.getNickName());
        nickName.getStyleClass().add("nickName");
        vBox.getChildren().add(nickName);
        Label signature = new Label(user.getSignature());
        signature.getStyleClass().add("signature");
        vBox.getChildren().add(signature);
        hBox.getChildren().add(vBox);
        this.getChildren().add(hBox);
        this.setId(user.getAccount());
        messageQueen = new LinkedList<>();
        chatRecordBox = new VBox(5);

    }

    public VBox getChatRecordBox() {
        return chatRecordBox;
    }

    public User getUser() {
        return user;
    }

    public Queue<String> getMessageQueen() {
        return messageQueen;
    }

    public void messageOffer(String message) {
        messageQueen.offer(message);
    }

    public String messagePoll() {
        return messageQueen.poll();
    }
}

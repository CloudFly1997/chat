package com.jack.chat.component;


import com.jack.chat.pojo.User;
import com.jfoenix.skins.JFXTextAreaSkin;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class FriendPane extends Pane {
    private User user;
    public FriendPane(User user) {
        this.user = user;
        HBox hBox = new HBox(1);
        hBox.setPrefSize(310,100);
        hBox.setStyle("-fx-background-color:#ABD8ED");
        hBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hBox.setStyle("-fx-background-color: #039ED3");
            }
        });
        hBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hBox.setStyle("-fx-background-color:#ABD8ED");
            }
        });
        ImageView imageView = new ImageView();
        imageView.setFitHeight(hBox.getPrefHeight() - 20);
        imageView.setFitWidth(hBox.getPrefHeight() - 20);
        imageView.setImage(new Image("img/下载.jpg"));
        hBox.setPadding(new Insets(10));
        hBox.getChildren().add(imageView);
        VBox vBox = new VBox();
        Label nickName = new Label(user.getNickName());
        nickName.setStyle("-fx-padding: 5 10;-fx-text-fill: #111111;-fx-font-family: 'Microsoft YaHei UI Light';-fx-font-size: 20;-fx-font-weight: 100");
        vBox.getChildren().add(nickName);
        Label signature = new Label(user.getSignature());
        signature.setStyle("-fx-padding: 10 10;-fx-text-fill: #aaaaaa;-fx-font-family: 'Microsoft YaHei UI Light';-fx-font-size: 15");
        vBox.getChildren().add(signature);
        hBox.getChildren().add(vBox);
        this.getChildren().add(hBox);
        this.setId(user.getAccount());
    }


    public User getUser() {
        return user;
    }
}

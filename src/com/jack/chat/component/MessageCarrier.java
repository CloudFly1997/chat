package com.jack.chat.component;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.css.PseudoClass;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;


public class MessageCarrier extends TextFlow {
    ImageView avatar = new ImageView();
    TextArea stringMessage = new TextArea();
    HBox hBox = new HBox(5);
    public MessageCarrier(String message) {
        this(false,message);
    }
    public MessageCarrier(boolean isSend,String message) {
        Text text = new Text();
        text.setText(message);
        hBox.setPrefWidth(600);
        avatar.setFitHeight(60);
        avatar.setFitWidth(60);

        avatar.setImage(new Image("img/avatar.jpg"));
        stringMessage.setPrefHeight(60);
        stringMessage.setMaxWidth(100);
        stringMessage.getStyleClass().add("stringMessage");
        stringMessage.setEditable(false);
        //stringMessage.setWrapText(true);
        stringMessage.setText(message);

        if(isSend) {
            this.setTextAlignment(TextAlignment.RIGHT);
            this.getChildren().addAll(stringMessage,avatar);
        }else {
            this.getChildren().addAll(avatar,stringMessage);
        }
    }
}

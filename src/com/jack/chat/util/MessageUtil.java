package com.jack.chat.util;

import com.jack.chat.common.MessageHandle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class MessageUtil {
    public static Node assembleMessage(String originalMessage,boolean isSelf) {
        TextFlow messageFlow = new TextFlow();
        HBox hBox = new HBox(10);
        messageFlow.setPrefWidth(605);
        ImageView avatar = new ImageView("file:///"+"C:\\Users\\jack\\Pictures\\avatar.jpg");
        avatar.setFitWidth(40);
        avatar.setFitHeight(40);
        TextArea textArea = new TextArea(MessageHandle.messageShow(originalMessage));
        textArea.setPrefHeight(40);
        //textArea.setMaxWidth(240);
        textArea.setBorder(Border.EMPTY);
        //textArea.setStyle("-fx-background-color: cyan;-fx-border-radius: 3;-fx-border-insets: 0;-fx-border-color: cyan");
        textArea.setWrapText(true);
        textArea.setEditable(false);
      /*  if (isSelf) {
            messageFlow.setTextAlignment(TextAlignment.RIGHT);
            hBox.getChildren().addAll(textArea,avatar);
        }else {
            hBox.getChildren().addAll(avatar,textArea);
        }*/
        hBox.getChildren().add(new Button());
        messageFlow.getChildren().add(hBox);
        return messageFlow;
    }
}

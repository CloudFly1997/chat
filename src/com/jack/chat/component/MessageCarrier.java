package com.jack.chat.component;

import com.jack.chat.util.*;
import com.jack.transfer.Message;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;


/**
 * @author jack
 */
public class MessageCarrier extends FlowPane {
    ImageView avatar = new ImageView();
    Node messageBody = null;
    public MessageCarrier(Message message) {
        this(false,message);
    }
    public MessageCarrier(boolean isSend, Message message) {
        this.setPadding(new Insets(5,5,0,5));
        this.setHgap(5);
        this.setRowValignment(VPos.TOP);
        avatar.setFitHeight(60);
        avatar.setFitWidth(60);
        AvatarLoad.loadAvatarById(avatar, message.getFromUser());
        messageBody = CalculateTextArea.getTextArea(message.getMessageContent());
        if (message.getMessageContent().startsWith(Command.IMG_NAME)) {
            FileUtil.downloadImg(message.getMessageContent().replace(Command.IMG_NAME, ""));
            messageBody = ImageLoad.loadImg(message.getMessageContent().replace(Command.IMG_NAME,""));
        }
        if(isSend) {
            this.setAlignment(Pos.TOP_RIGHT);
            this.getChildren().addAll(messageBody,avatar);
        }else {
            this.getChildren().addAll(avatar,messageBody);
        }
    }
}

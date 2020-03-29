package com.jack.chat.component;

import com.jack.chat.util.AvatarLoad;
import com.jack.chat.util.CalculateTextArea;
import com.jack.transfer.Message;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;


/**
 * @author jack
 */
public class MessageCarrier extends FlowPane {
    ImageView avatar = new ImageView();
    TextArea stringMessage;
    public MessageCarrier(Message message) {
        this(false,message);
    }
    public MessageCarrier(boolean isSend, Message message) {
        this.setPadding(new Insets(5,5,0,5));

        this.setHgap(5);
        this.setRowValignment(VPos.TOP);
        avatar.setFitHeight(60);
        avatar.setFitWidth(60);
        AvatarLoad.loadChatAvatar(avatar, message.getFromUser());
        stringMessage = CalculateTextArea.getTextArea(message.getMessageContent());

        if(isSend) {
            this.setAlignment(Pos.TOP_RIGHT);
            this.getChildren().addAll(stringMessage,avatar);
        }else {
            this.getChildren().addAll(avatar,stringMessage);
        }
    }
}

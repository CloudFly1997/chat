package com.jack.chat.component;

import com.jack.chat.common.GroupPaneHolder;
import com.jack.chat.util.*;
import com.jack.transfer.Message;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


/**
 * @author jack
 */
public class MessageCarrier extends FlowPane {
    ImageView avatar = new ImageView();
    Node messageContent = null;
    public MessageCarrier(Message message) {
        this(false,message);
    }
    public MessageCarrier(boolean isSend, Message message) {
        this.setPadding(new Insets(5,5,0,5));
        this.setHgap(5);
        this.setRowValignment(VPos.TOP);
        avatar.setFitHeight(60);
        avatar.setFitWidth(60);
        AvatarUtil.loadAvatarById(avatar, message.getFromUser());
        messageContent = CalculateTextArea.getTextArea(message.getMessageContent());
        VBox messageBody = new VBox(5);
        Text from = new Text();
        if (Command.GROUP.equals(message.getType())) {
            from.setText(GroupPaneHolder.getInstance().getGroupPane(message.getToUser()).getGroup()
                    .getMembers().get(message.getFromUser()).getNickName());
        }
        from.wrappingWidthProperty().bind(messageBody.widthProperty());
        if (message.getMessageContent().startsWith(Command.IMG_NAME)) {
            FileUtil.downloadImg(message.getMessageContent().replace(Command.IMG_NAME, ""));
            messageContent = ImageLoad.loadImg(message.getMessageContent().replace(Command.IMG_NAME,""));
        }
        messageBody.getChildren().addAll(from, messageContent);
        if(isSend) {
            from.setTextAlignment(TextAlignment.RIGHT);
            this.setAlignment(Pos.TOP_RIGHT);
            this.getChildren().addAll(messageBody,avatar);
        }else {
            this.getChildren().addAll(avatar, messageBody);
        }
    }
}

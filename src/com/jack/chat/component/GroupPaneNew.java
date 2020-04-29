package com.jack.chat.component;

import com.jack.chat.common.Session;
import com.jack.chat.pojo.Group;
import com.jack.chat.service.imp.MessageServiceImpl;
import com.jack.chat.util.AvatarUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/31 22:21
 */

public class GroupPaneNew extends IndividualPane {
    private Group group;
    public Label groupName;
    public ImageView groupAvatar;

    public GroupPaneNew(Group group) {
        super();
        init();
        this.group = group;
        this.groupName.setText(group.getGroupName());
        AvatarUtil.loadAvatar(groupAvatar, group);
    }

    @Override
    void init() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/groupPane.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    void setRightClickEvent() {

    }

    @Override
    void setDoubleClickEvent() {

    }

    @Override
    void receiveMessage(Node node) {
        if (!group.getGroupAccount().equals(session.getCurrentChatWith())) {
            unReadMessageCountAdd();
        }
        messageBox.getChildren().add(node);
    }

    @Override
    void chat() {
        if (!group.getGroupAccount().equals(session.getCurrentChatWith())) {
            session.setCurrentChatWith(group.getGroupAccount());
            mainWindow.chatWith.setText(group.getGroupName());
            mainWindow.messageAreaScrollPane.setContent(messageBox);
        }
        new MessageServiceImpl().makeRead(Session.getInstance().getUser(), group);
        setUnReadMessageCountLabel(0);
    }

}

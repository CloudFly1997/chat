package com.jack.chat.component;

import com.jack.chat.common.MainWindowHolder;
import com.jack.chat.controller.MainWindow;
import com.jack.chat.util.PlaySound;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/4/13 22:57
 */

public class NotifyPane extends HBox {
    public ImageView avatar;
    public VBox chatRecordBox;
    public Label unReadMessageCountLabel, name;
    public int unReadMessageCount;
    MainWindow mainWindow = MainWindowHolder.getInstance().getMainWindow();

    public NotifyPane() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/notifyPane.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        chatRecordBox = new VBox(5);
        this.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                //双击事件
            }
            if (event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                chat();
            } else if (event.getButton().name().equals(MouseButton.SECONDARY.name())) {
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
        chatRecordBox.getChildren().add(node);
        PlaySound.playSoundWhenReceiveMessage();
    }

    public void unReadMessageCountAdd() {
        unReadMessageCount++;//未读消息加1
        unReadMessageCountLabel.setText(String.valueOf(unReadMessageCount));
    }

    public void chat() {
        mainWindow.chatWith.setText(name.getText());
        this.getChatRecordBox().heightProperty().addListener((observable, oldValue, newValue) -> mainWindow.messageAreaScrollPane.setVvalue(1));
        mainWindow.messageAreaScrollPane.setContent(this.getChatRecordBox());
    }
}

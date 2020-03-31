package com.jack.chat.component;

import com.jack.chat.common.MainWindowHolder;
import com.jack.chat.common.Session;
import com.jack.chat.controller.MainWindow;
import com.jack.chat.service.MessageService;
import com.jack.chat.service.imp.MessageServiceImpl;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/31 0:15
 */

public abstract class IndividualPane extends Pane {
    public VBox messageBox;
    public Label unReadMessageCountLabel;
    MainWindow mainWindow = MainWindowHolder.getInstance().getMainWindow();
    Session session = Session.getInstance();
    MessageService messageService = new MessageServiceImpl();
    public int unReadMessageCount;

    public IndividualPane() {
        messageBox = new VBox(5);
        messageBox.heightProperty().addListener((observable, oldValue, newValue) -> mainWindow.messageAreaScrollPane.setVvalue(1));
        this.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                //双击事件
            }
            if (event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                chat();
            } else if (event.getButton().name().equals(MouseButton.SECONDARY.name())) {
                setRightClickEvent();
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

    /**
     * 初始化，读取fxml文件
     */
    abstract void init();

    public VBox getMessageBox() {
        this.unReadMessageCount = 0;
        unReadMessageCountLabel.setText(String.valueOf(unReadMessageCount));
        return messageBox;
    }

    /**
     * 设置鼠标右击事件
     */
    abstract void setRightClickEvent();

    /**
     * 设置鼠标双击事件
     */
    abstract void setDoubleClickEvent();

    /**
     * 接收消息
     * @param node
     */
    abstract void receiveMessage(Node node);

    public void setUnReadMessageCountLabel(int unReadMessageCount) {
        this.unReadMessageCountLabel.setText(String.valueOf(unReadMessageCount));
    }



    public void unReadMessageCountAdd() {
        unReadMessageCount++;//未读消息加1
        unReadMessageCountLabel.setText(String.valueOf(unReadMessageCount));
    }


    /**
     * 点击调用聊天方法
     */
    abstract void chat();
}

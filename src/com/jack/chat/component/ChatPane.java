package com.jack.chat.component;

import com.jack.chat.common.SessionHolder;
import com.jack.chat.pojo.User;
import com.jack.chat.util.MessageHandle;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jack
 */
public class ChatPane extends Pane {
    VBox vBox;
    JFXButton messageSendButton;
    TextArea messageEditArea;
    String account;
    DataOutputStream dos = SessionHolder.getInstance().getSession().getDos();
    String userName = SessionHolder.getInstance().getSession().getUser().getAccount();

    public ChatPane(User user) {
        this.account = user.getAccount();
        init();
    }

    public void init() {
        this.setPrefSize(750, 515);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(600, 305);
        scrollPane.setFitToWidth(true);
        vBox = new VBox(10);
        vBox.setPrefSize(600, 305);
        vBox.setStyle("-fx-background-color: white");
        scrollPane.setContent(vBox);
        vBox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scrollPane.setVvalue(1);
            }
        });
        messageEditArea = new TextArea();
        messageEditArea.setWrapText(true);
        messageEditArea.setPrefSize(600, 180);
        messageEditArea.setId("messageEditArea");
        messageEditArea.setLayoutY(310);
        messageSendButton = new JFXButton("发送");
        messageSendButton.setButtonType(JFXButton.ButtonType.RAISED);
        messageSendButton.setPrefSize(75, 30);
        messageSendButton.setStyle("-fx-background-color: #ABD8ED");
        messageSendButton.setLayoutX(520);
        messageSendButton.setLayoutY(430);
        messageSendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    sendMessage();
                    vBox.getChildren().add(new MessageCarrier(true,messageEditArea.getText()));
                    messageEditArea.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        this.getChildren().addAll(scrollPane, messageEditArea, messageSendButton);
        this.setLayoutX(312);
        this.setLayoutY(150);
    }


    public void setMessage(String message) {
        vBox.getChildren().add(new MessageCarrier(message));

    }

    public String getAccount() {
        return account;
    }

    public VBox getVBox() {
        return vBox;
    }

    public JFXButton getMessageSendButton() {
        return messageSendButton;
    }

    public TextArea getMessageEditArea() {
        return messageEditArea;
    }

    public void sendMessage() throws IOException {
        String message = messageEditArea.getText();
            if ("".equals(message) || message == null) {
                JFXAlert jfxAlert = new JFXAlert();
                jfxAlert.initStyle(StageStyle.UTILITY);
                jfxAlert.setSize(400, 150);
                jfxAlert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                jfxAlert.setHeaderText("警告");
                jfxAlert.setContent(new Text("消息不能为空"));
                jfxAlert.showAndWait();
                return;
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date now = new Date();
                String date = dateFormat.format(now);
                message = MessageHandle.afterHandleMessage(userName, account, date, messageEditArea.getText());
                dos.writeUTF(message);
                dos.flush();
            }
    }
}

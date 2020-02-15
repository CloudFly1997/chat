package com.jack.chat.controller;

import com.jack.chat.common.MessageHandle;
import com.jack.chat.common.PlaySound;
import com.jack.chat.service.ReceiveMessageService;
import com.jack.chat.util.MessageUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

public class ChatWindowController implements Initializable {


    public ScrollPane scrollPanel;
    public TextArea messageEditArea;
    public VBox friendsList;
    public VBox messageShowArea;
    public Pane chatPane;
    public String toUser;
    public Socket createSocket;
    public DataInputStream dis;
    public DataOutputStream dos;
    public TextField userNameField;
    public String userName;
    public boolean connect_state;
    public StringBuilder getMessage;
    public Alert alert;


    public void sendMessage() throws IOException {
        alert = new Alert(Alert.AlertType.WARNING);
        String message = messageEditArea.getText();
        if (connect_state) {
            if ("".equals(message) || message == null) {
                alert.setContentText("消息不能为空");
                alert.showAndWait();
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date now = new Date();
                String date = dateFormat.format(now);
                message = MessageHandle.afterHandleMessage(userName, toUser, date, messageEditArea.getText());
                dos.writeUTF(message);
                dos.flush();
                //TextFlow textFlow = new TextFlow(new Button());
                TextFlow textFlow = (TextFlow) MessageUtil.assembleMessage(message, true);
                friendsList.getChildren().add(new Button());
                messageShowArea.getChildren().add(textFlow);
                messageEditArea.clear();
            }
        } else {
            alert.setTitle("警告");
            alert.setContentText("服务器未连接");
            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addFriends();
        messageShowArea.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scrollPanel.setVvalue(1);
            }
        });
        connect_state = false;
    }

    public void addFriends() {
        Random random = new Random();
        int r = random.nextInt(20);
        for (int i = 0; i <= r; i++) {
            String friendName = "好友" + i;
            Button button = new Button(friendName);
            button.setPrefHeight(78);
            button.setPrefWidth(194);
            button.setId(friendName);
            button.setOnAction(event -> {
                toUser = button.getId();
                chatPane.setVisible(true);
                chatPane.setManaged(true);
            });
            friendsList.getChildren().add(button);
        }
    }

    public void connectToServer() { //
        try {
            createSocket = new Socket("127.0.0.1", 8888);
            dis = new DataInputStream(createSocket.getInputStream());
            dos = new DataOutputStream(createSocket.getOutputStream());
            userName = userNameField.getText();
            dos.writeUTF(userName);
            //new Thread(new ReceiveMessageThread()).start();
            connect_state = true;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("连接成功");
            alert.showAndWait();
            //ReceiveMessageService receiveMessageService = new ReceiveMessageService(messageShowArea, dis);
            //receiveMessageService.start();
        } catch (UnknownHostException e) {
            alert.setContentText("无法连接服务器");
        } catch (IOException e) {
            alert.setContentText("程序出错");
        }
    }

    public void receiveMessage() {
        PlaySound playSound = new PlaySound();
        // Connection conn = DbUtil.getConnection();
//            String sql = "SELECT * FROM message WHERE toUser = ? AND fromUser = ? AND isRead = 0";
//            try {
//                PreparedStatement preparedStatement = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
//                preparedStatement.setString(1, userName);
//                preparedStatement.setString(2, toUser);
//                ResultSet resultSet = preparedStatement.executeQuery();
//                while (resultSet.next()) {
//                    String messageDate = resultSet.getString("messageDate");
//                    String fromUser = resultSet.getString("fromUser");
//                    String content = resultSet.getString("content");
//                    TextArea textArea = new TextArea();
//                    textArea.setStyle("-fx-border-radius: 0;-fx-background-color: transparent");
//                    textArea.setEditable(false);
//                    textArea.setText(MessageHandle.messageShow(messageDate, fromUser, content));
//                    messageShowArea.getChildren().add(textArea);
//                    resultSet.updateBoolean("isRead", true);
//                    resultSet.updateRow();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//                System.out.println("消息拉取出错");
//            }


    }
}

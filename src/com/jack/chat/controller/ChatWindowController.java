package com.jack.chat.controller;

import com.jack.chat.common.ChatPaneHolder;
import com.jack.chat.common.Session;
import com.jack.chat.common.SessionHolder;
import com.jack.chat.component.ChatPane;
import com.jack.chat.component.FriendPane;
import com.jack.chat.pojo.User;
import com.jack.chat.service.ReceiveMessageService;
import com.jack.chat.service.UserService;
import com.jack.chat.service.imp.UserServiceImp;
import com.jack.chat.util.MessageHandle;
import com.jack.chat.util.PlaySound;
import com.jack.chat.util.MessageUtil;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Window;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ChatWindowController implements Initializable {


    public ScrollPane scrollPanel;
    public TextArea messageEditArea;
    public VBox messageShowArea;
    public Pane chatPane;
    public String toUser;
    public DataInputStream dis;
    public DataOutputStream dos;
    public TextField userNameField;
    public String userName;
    public boolean connect_state;
    public StringBuilder getMessage;
    public Alert alert;
    public VBox friendListPane;
    public AnchorPane whole;
    private Double offsetX;
    private Double offsetY;


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
                TextFlow textFlow = (TextFlow) MessageUtil.assembleMessage(message, true);
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
        whole.setOnMousePressed(event -> {
            Window window = whole.getScene().getWindow();
            //             鼠标在屏幕中的坐标，    窗体在屏幕中的坐标
            this.offsetX = event.getScreenX() - window.getX();
            this.offsetY = event.getScreenY() - window.getY();
        });
        whole.setOnMouseDragged(event -> {
            Window window = whole.getScene().getWindow();
            //   新的鼠标位置-旧的鼠标位置+旧的窗体位置
            // = 鼠标的偏移量+旧的窗体位置
            window.setX(event.getScreenX() - this.offsetX);
            window.setY(event.getScreenY() - this.offsetY);
        });
        messageShowArea.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scrollPanel.setVvalue(1);
            }
        });
        Session session = SessionHolder.getInstance().getSession();
        dis = session.getDis();
        dos = session.getDos();
        ChatPaneHolder chatPaneHolder = ChatPaneHolder.getInstance();
        UserService userService = new UserServiceImp();
        List<User> friendsList = userService.getFriendsList(session.getUser().getAccount());
        for (User user : friendsList) {
            FriendPane friendPane = new FriendPane(user);
            friendPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(!chatPaneHolder.contain(friendPane.getUser())){
                        ChatPane chatPane = new ChatPane(friendPane.getUser());
                        chatPaneHolder.addChatPane(friendPane.getUser(), chatPane);
                    }
                    whole.getChildren().remove(chatPaneHolder.getCurrentChatPane());
                    whole.getChildren().add(chatPaneHolder.getChatPane(friendPane.getUser()));
                    chatPaneHolder.setCurrentChatPane(friendPane.getUser());
                }
            });
            friendListPane.getChildren().add(friendPane);
        }

        connect_state = true;

        ReceiveMessageService receiveMessageService = new ReceiveMessageService(messageShowArea, dis);
        receiveMessageService.start();
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

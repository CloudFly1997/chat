package com.jack.chat.controller;

import com.jack.chat.common.ChatPaneHolder;
import com.jack.chat.common.FriendPaneHolder;
import com.jack.chat.common.Session;
import com.jack.chat.common.SessionHolder;
import com.jack.chat.component.ChatPane;
import com.jack.chat.component.FriendPane;
import com.jack.chat.pojo.User;
import com.jack.chat.service.ReceiveMessageService;
import com.jack.chat.service.UserService;
import com.jack.chat.service.imp.UserServiceImp;
import com.jack.chat.util.PlaySound;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;
import java.util.List;
import java.util.Queue;
import java.util.ResourceBundle;

public class ChatWindowController implements Initializable {


    public DataInputStream dis;
    public DataOutputStream dos;
    public boolean connect_state;
    public VBox friendListPane;
    public Pane whole;
    public Label nickNameLabel;
    public Label signatureLabel;
    public ImageView userAvatar;
    public Label close;
    public Label maximize;
    public Label minimize;
    private Double offsetX;
    private Double offsetY;
    private ChatPane currentChatPane;
    private ChatPaneHolder chatPaneHolder = ChatPaneHolder.getInstance();
    private FriendPaneHolder friendPaneHolder = FriendPaneHolder.getInstance();
    private Session session;
    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //关闭
        close.setOnMouseClicked(event -> {
            Platform.exit();
        });
        // 最小化
        minimize.setOnMouseClicked(event -> {
            Stage stage = (Stage) whole.getScene().getWindow();
            stage.setIconified(true);
        });

        // 最大化
        maximize.setOnMouseClicked(event -> {
            Stage stage = (Stage) whole.getScene().getWindow();
            // 最大化，取消最大化
            stage.setMaximized(stage.maximizedProperty().not().get());
        });

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
        session = SessionHolder.getInstance().getSession();
        user = session.getUser();
        userAvatar.setImage(new Image("img/下载.jpg"));
        nickNameLabel.setText(user.getNickName());
        signatureLabel.setText(user.getSignature());
        dis = session.getDis();
        dos = session.getDos();


        UserService userService = new UserServiceImp();
        List<User> friendsList = userService.getFriendsList(session.getUser().getAccount());
        for (User user : friendsList) {
            FriendPane friendPane = new FriendPane(user);
            friendPane.setOnMouseClicked(event -> {
                Queue<String> messageQueue = friendPane.getMessageQueen();

                setChatPane(messageQueue, friendPane.getUser());
            });
            friendPaneHolder.addFriendPane(user.getAccount(), friendPane);
            friendListPane.getChildren().add(friendPane);
        }
        connect_state = true;
        ReceiveMessageService receiveMessageService = new ReceiveMessageService();
        receiveMessageService.start();
    }

    private void setChatPane(Queue<String> messageQueue, User user) {
        if (!chatPaneHolder.contain(user)) {
            ChatPane chatPane = new ChatPane(user);
            chatPaneHolder.addChatPane(user.getAccount(), chatPane);
        }
        ChatPane chatPane = chatPaneHolder.getChatPane(user.getAccount());
        /**
         * 铭记这个低级错误！！！
         */
        /*for (int i = 0; i<messageQueue.size(); i++) {
            messageQueue.poll();
        }*/
        while (!messageQueue.isEmpty()) {
            chatPane.setMessage(messageQueue.poll());
        }
        whole.getChildren().remove(this.currentChatPane);
        whole.getChildren().add(chatPane);
        chatPaneHolder.setCurrentChatPane(user);
        this.currentChatPane = chatPane;
    }

    /**
     * 登录之后拉取未读消息
     */
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

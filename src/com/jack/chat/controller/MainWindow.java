package com.jack.chat.controller;

import com.jack.chat.common.FriendPaneHolder;
import com.jack.chat.common.Session;
import com.jack.chat.common.SessionHolder;
import com.jack.chat.component.FriendPane;
import com.jack.chat.component.MessageCarrier;
import com.jack.chat.pojo.User;
import com.jack.chat.service.ReceiveMessageService;
import com.jack.chat.service.UserService;
import com.jack.chat.service.imp.UserServiceImp;
import com.jack.chat.util.MessageHandle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/1 22:08
 */

public class MainWindow implements Initializable {
    /**
     * 总窗体
     */
    public GridPane root;
    /**
     * 第一行
     */
    public RowConstraints row1;
    /**
     * 第二行
     */
    public RowConstraints row2;
    /**
     * 主要窗口
     */
    public SplitPane main;
    /**
     * 好友列表区域板
     */
    public TabPane friendListPane;
    /**
     * 聊天消息呈现区
     */
    public ScrollPane messageAreaScrollPane;
    /**
     * 右边区域
     */
    public AnchorPane right;
    /**
     * 聊天对象
     */
    public Label chatWith;
    /**
     * 最小化
     */
    public Label minimize;
    /**
     * 最大化
     */
    public Label maximize;
    /**
     * 关闭
     */
    public Label close;
    public VBox friendList;
    public ImageView userAvatar;
    public Session session;
    public User user;
    public DataInputStream dis;
    public DataOutputStream dos;
    public User currentChatWith;
    public Label userName;
    public TextArea messageEditArea;
    private FriendPaneHolder friendPaneHolder = FriendPaneHolder.getInstance();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//关闭
        close.setOnMouseClicked(event -> {
            Platform.exit();
        });
        // 最小化
        minimize.setOnMouseClicked(event -> {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setIconified(true);
        });

        // 最大化
        maximize.setOnMouseClicked(event -> {
            Stage stage = (Stage) root.getScene().getWindow();
            // 最大化，取消最大化
            stage.setMaximized(stage.maximizedProperty().not().get());
        });
        friendListPane.prefHeightProperty().bind(main.heightProperty());
        messageAreaScrollPane.prefHeightProperty().bind(main.heightProperty().multiply(0.6));
        messageAreaScrollPane.prefWidthProperty().bind(right.widthProperty());

        session = SessionHolder.getInstance().getSession();
        user = session.getUser();
        userAvatar.setImage(new Image("img/下载.jpg"));
        userName.setText(user.getNickName());
        dis = session.getDis();
        dos = session.getDos();
        UserService userService = new UserServiceImp();
        List<User> friendsList = userService.getFriendsList(session.getUser().getAccount());
        for (User user : friendsList) {
            FriendPane friendPane = null;
            try {
                friendPane = new FriendPane(user);
                friendPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println(1);
                        setChatWith(user);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            friendPaneHolder.addFriendPane(user.getAccount(), friendPane);
            friendList.getChildren().add(friendPane);
        }
        ReceiveMessageService receiveMessageService = new ReceiveMessageService();
        receiveMessageService.start();
    }

    public void setChatWith(User user) {
        if (user != currentChatWith) {
            this.currentChatWith = user;
            this.chatWith.setText(user.getNickName());
            chatWith.setText(user.getNickName());
            messageAreaScrollPane.setContent(friendPaneHolder.getFriendPane(user.getAccount()).getChatRecordBox());
        }
    }

    public void sendMessage() throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String date = dateFormat.format(now);
        String originMessage = messageEditArea.getText();
        String message = MessageHandle.afterHandleMessage(user.getAccount(), currentChatWith.getAccount(), date,
                originMessage);
        dos.writeUTF(message);
        dos.flush();
        messageEditArea.clear();
        friendPaneHolder.getFriendPane(currentChatWith.getAccount()).getChatRecordBox().getChildren().add(new MessageCarrier(true,originMessage));
    }
}

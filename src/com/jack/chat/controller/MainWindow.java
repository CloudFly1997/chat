package com.jack.chat.controller;

import com.jack.chat.common.FriendPaneHolder;
import com.jack.chat.common.Session;
import com.jack.chat.common.SessionHolder;
import com.jack.chat.component.FriendPane;
import com.jack.chat.pojo.User;
import com.jack.chat.service.UserService;
import com.jack.chat.service.imp.UserServiceImp;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.URL;
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
        dis = session.getDis();
        dos = session.getDos();
        UserService userService = new UserServiceImp();
        List<User> friendsList = userService.getFriendsList(session.getUser().getAccount());
        for (User user : friendsList) {
            FriendPane friendPane = new FriendPane(user);
            friendPane.setOnMouseClicked(event -> {
                if(currentChatWith != friendPane.getUser()){
                    messageAreaScrollPane.setContent(friendPane.getChatRecordBox());
                }
            });
            friendPaneHolder.addFriendPane(user.getAccount(), friendPane);
            friendList.getChildren().add(friendPane);
        }
    }

}

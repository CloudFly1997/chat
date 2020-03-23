package com.jack.chat.controller;

import com.jack.chat.common.FriendPaneHolder;
import com.jack.chat.common.MainWindowHolder;
import com.jack.chat.common.Session;
import com.jack.chat.component.FriendPane;
import com.jack.chat.component.MessageCarrier;
import com.jack.chat.component.SearchPane;
import com.jack.chat.pojo.User;
import com.jack.chat.service.FriendService;
import com.jack.chat.service.UserService;
import com.jack.chat.service.imp.FriendServiceImpl;
import com.jack.chat.service.imp.UserServiceImpl;
import com.jack.chat.thread.ReceiveMessageService;
import com.jack.chat.util.AvatarLoad;
import com.jack.chat.util.MessageHandle;
import com.jack.chat.util.TimeUtil;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
    public  VBox friendList;
    public ImageView userAvatar;
    public Session session;
    public User user;
    public DataInputStream dis;
    public DataOutputStream dos;
    public User currentChatWith;
    public Label userName;
    public TextArea messageEditArea;
    public StackPane searchArea;
    public Label search;
    public TextField searchField;
    private FriendPaneHolder friendPaneHolder = FriendPaneHolder.getInstance();
    private Double offsetX;
    private Double offsetY;
    UserService userService = UserServiceImpl.getInstance();

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
        root.setOnMousePressed(event -> {
            Window window = root.getScene().getWindow();
            //             鼠标在屏幕中的坐标，    窗体在屏幕中的坐标
            this.offsetX = event.getScreenX() - window.getX();
            this.offsetY = event.getScreenY() - window.getY();
        });
        root.setOnMouseDragged(event -> {
            Window window = root.getScene().getWindow();
            //   新的鼠标位置-旧的鼠标位置+旧的窗体位置
            // = 鼠标的偏移量+旧的窗体位置
            window.setX(event.getScreenX() - this.offsetX);
            window.setY(event.getScreenY() - this.offsetY);
        });

        search.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new SearchPane();
                System.out.println("1231232131");
            }
        });
        friendListPane.prefHeightProperty().bind(main.heightProperty());
        messageAreaScrollPane.prefHeightProperty().bind(main.heightProperty().multiply(0.6));
        messageAreaScrollPane.prefWidthProperty().bind(right.widthProperty());
        session = Session.getInstance();
        user = session.getUser();
        AvatarLoad.loadUserAvatar(userAvatar, user.getAccount());
        userName.setText(user.getNickName());
        dis = session.getDis();
        dos = session.getDos();
        FriendService friendService = FriendServiceImpl.getInstance();
        MainWindowHolder.getInstance().setMainWindow(this);

        List<User> friendsList = friendService.getFriendsList(session.getUser().getAccount());
        initFriendPane(friendsList);
        ReceiveMessageService receiveMessageService = new ReceiveMessageService();
        receiveMessageService.start();

    }

    public void initFriendPane(List<User> friendsList) {
        for (User user : friendsList) {
            FriendPane friendPane = new FriendPane(user);
            friendPaneHolder.addFriendPane(user.getAccount(), friendPane);
            friendList.getChildren().add(friendPane);
        }
    }

    public void sendMessage() throws IOException {
        String originMessage = messageEditArea.getText();
        String type = "[TXT]";
        String message = MessageHandle.afterHandleMessage(type, user.getAccount(),
                currentChatWith.getAccount(),
                TimeUtil.getNowTime(),
                originMessage);
        dos.writeUTF(message);
        dos.flush();
        messageEditArea.clear();
        friendPaneHolder.getFriendPane(currentChatWith.getAccount()).getChatRecordBox().getChildren().add(new MessageCarrier(true, originMessage));
    }


}

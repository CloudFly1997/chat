package com.jack.chat.controller;

import com.jack.chat.common.FriendPaneHolder;
import com.jack.chat.common.GroupPaneHolder;
import com.jack.chat.common.MainWindowHolder;
import com.jack.chat.common.Session;
import com.jack.chat.component.*;
import com.jack.chat.pojo.Group;
import com.jack.chat.pojo.User;
import com.jack.chat.service.FriendService;
import com.jack.chat.service.GroupService;
import com.jack.chat.service.imp.FriendServiceImpl;
import com.jack.chat.service.imp.GroupServiceImpl;
import com.jack.chat.thread.ReceiveMessageService;
import com.jack.chat.util.AvatarLoad;
import com.jack.chat.util.Command;
import com.jack.transfer.Message;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.net.Socket;
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
    public VBox friendListBox;
    public ImageView userAvatar;
    public Session session;
    public User user;
    public ObjectInputStream ois;
    public ObjectOutputStream oos;
    public User currentChatWith;
    public Label userName;
    public TextArea messageEditArea;
    public Label addFriend;
    public Label addGroup;
    public Label sendFile;
    public Label sendImg;
    public Label createGroup;
    public VBox groupListBox;
    private FriendPaneHolder friendPaneHolder = FriendPaneHolder.getInstance();
    private GroupPaneHolder groupPaneHolder = GroupPaneHolder.getInstance();
    private Double offsetX;
    private Double offsetY;

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

        addFriend.setOnMouseClicked(event -> {
            if (event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                new SearchPane().show();
            }
        });
        addGroup.setOnMouseClicked(event -> {
            if (event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                new SearchPane().show();
            }
        });

        createGroup.setOnMouseClicked(event -> {
            if (event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                new CreateGroupPane().show();
            }
        });

        friendListPane.prefHeightProperty().bind(main.heightProperty());
        messageAreaScrollPane.prefHeightProperty().bind(main.heightProperty().multiply(0.6));
        messageAreaScrollPane.prefWidthProperty().bind(right.widthProperty());
        session = Session.getInstance();
        user = session.getUser();
        AvatarLoad.loadUserAvatar(userAvatar, user.getAccount());
        userAvatar.setOnMouseClicked(e -> {
            new ProfilePane(user).show();
        });
        userName.setText(user.getNickName());
        ois = session.getOis();
        oos = session.getOos();
        MainWindowHolder.getInstance().setMainWindow(this);
        initFriendPane();
        initGroupPane();



        ReceiveMessageService receiveMessageService = new ReceiveMessageService();
        receiveMessageService.start();

    }

    public void initFriendPane() {
        FriendService friendService = FriendServiceImpl.getInstance();
        List<User> friendList = friendService.getFriendsList(user.getAccount());
        for (User user : friendList) {
            FriendPane friendPane = new FriendPane(user);
            friendPaneHolder.addFriendPane(user.getAccount(), friendPane);
            friendListBox.getChildren().add(friendPane);
        }
    }

    public void initGroupPane() {
        GroupService groupService = GroupServiceImpl.getInstance();
        List<Group> groupList = groupService.query(user.getAccount());
        for (Group group : groupList) {
            GroupPane groupPane = new GroupPane(group);
            groupPaneHolder.addGroupPane(group.getGroupAccount(), groupPane);
            groupListBox.getChildren().add(groupPane);
        }
    }

    public void sendMessage() throws IOException {
        String originMessage = messageEditArea.getText();
        Message message = new Message(user.getAccount(),
                currentChatWith.getAccount(), originMessage, Command.TXT);
        oos.writeObject(message);
        messageEditArea.clear();
        friendPaneHolder.getFriendPane(currentChatWith.getAccount()).getChatRecordBox().getChildren().add(new MessageCarrier(true, message));
    }


    public void sendFile(MouseEvent mouseEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择图片");
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
/*        FileMessage fileMessage = new FileMessage(file, user.getAccount(),
                currentChatWith.getAccount(), Command.IMAGE,file.getName());
        oos.writeObject(fileMessage);*/
        Socket socket = new Socket("127.0.0.1", 9999);
/*        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());*/

        FileInputStream fis = new FileInputStream(file);

        OutputStream outputStream = socket.getOutputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = fis.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
        }
        socket.shutdownOutput();
        fis.close();
        outputStream.close();
    }

    public void sendImg(MouseEvent mouseEvent) {
        new FileChooser().showOpenDialog(root.getScene().getWindow());
    }
}

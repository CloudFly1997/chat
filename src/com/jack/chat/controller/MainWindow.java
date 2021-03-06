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
import com.jack.chat.task.ReceiveMessageService;
import com.jack.chat.util.AvatarUtil;
import com.jack.chat.util.Command;
import com.jack.chat.util.FileUtil;
import com.jack.chat.util.PropertiesUtil;
import com.jack.transfer.Message;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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

    public GridPane root;
    public SplitPane main;
    public TabPane friendListPane;
    public ScrollPane messageAreaScrollPane;
    public AnchorPane right;
    public Label chatWith;
    public Label minimize;
    public Label maximize;
    public Label close;
    public VBox friendListBox;
    public ImageView userAvatar;
    public Session session;
    public User user;
    public ObjectInputStream ois;
    public ObjectOutputStream oos;
    public Label userName;
    public TextArea messageEditArea;
    public Label addFriend;
    public Label addGroup;
    public Label sendFile;
    public Label sendImg;
    public Label createGroup;
    public VBox groupListBox;
    public VBox notifyBox;
    public Button sendButton;
    private FriendPaneHolder friendPaneHolder = FriendPaneHolder.getInstance();
    private GroupPaneHolder groupPaneHolder = GroupPaneHolder.getInstance();
    private Double offsetX;
    private Double offsetY;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //关闭
        close.setOnMouseClicked(event -> {
            FileUtil.deleteAvatarFile();
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

        messageEditArea.setOnKeyPressed(e -> {

            if (e.isShiftDown() && e.getCode() == KeyCode.ENTER) {
                messageEditArea.appendText("\n");
                return;
            }
            if (e.getCode() == KeyCode.ENTER) {
                sendMessage();
                e.consume();
            }

        });
        Tooltip addFriendTooltip = new Tooltip("搜索好友");
        Tooltip addGroupTooltip = new Tooltip("搜索群");
        Tooltip createGroupTooltip = new Tooltip("创建群");
        addFriend.setTooltip(addFriendTooltip);
        addFriend.setOnMouseClicked(event -> {
            if (event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                new SearchFriendPane().show();
            }
        });

        addGroup.setTooltip(addGroupTooltip);
        addGroup.setOnMouseClicked(event -> {
            if (event.getButton().name().equals(MouseButton.PRIMARY.name())) {
                new SearchGroupPane().show();
            }
        });

        createGroup.setTooltip(createGroupTooltip);
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
        AvatarUtil.loadAvatar(userAvatar, user);
        userAvatar.setOnMouseClicked(e -> {
            new ProfilePane(user).show();
        });
        userName.setText(user.getNickName() == null ? user.getAccount() : user.getNickName());
        ois = session.getOis();
        oos = session.getOos();
        MainWindowHolder.getInstance().setMainWindow(this);
        initFriendPane();
        initGroupPane();
        NotifyPane notifyPane = new NotifyPane();
        notifyBox.getChildren().add(notifyPane);
        ReceiveMessageService receiveMessageService = new ReceiveMessageService();
        receiveMessageService.start();

    }

    public void initFriendPane() {
        FriendService friendService = FriendServiceImpl.getInstance();
        List<User> friendList = friendService.getFriendsList(user.getAccount());
        for (User user : friendList) {
            FriendPane friendPane = new FriendPane(user);
            session.getUserMap().put(user.getAccount(), user);
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

    public void sendMessage() {
        String originMessage = messageEditArea.getText();
        if (("").equals(originMessage)) {
            return;
        }
        String type = session.getCurrentChatWithType();
        Message message = new Message(user.getAccount(),
                session.getCurrentChatWith(), originMessage, type);
        try {
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        messageEditArea.clear();
        if (type.equals(Command.FRIEND)) {
            friendPaneHolder.getFriendPane(session.getCurrentChatWith()).getChatRecordBox().getChildren().add(new MessageCarrier(true, message));
        } else if (type.equals(Command.GROUP)) {
            groupPaneHolder.getGroupPane(session.getCurrentChatWith()).getChatRecordBox().getChildren().add(new MessageCarrier(true, message));
        }
    }


    public void sendFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件");
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        if (file != null) {
            String type = session.getCurrentChatWithType();
            // String fileName = System.currentTimeMillis() + "/" + file.getName();
            Message message = new Message(user.getAccount(),
                    session.getCurrentChatWith(), Command.FILE_NAME + file.getAbsolutePath(), type);

            if (type.equals(Command.FRIEND)) {
                friendPaneHolder.getFriendPane(session.getCurrentChatWith()).getChatRecordBox().getChildren().add(new MessageCarrier(true, message));
            } else if (type.equals(Command.GROUP)) {
                groupPaneHolder.getGroupPane(session.getCurrentChatWith()).getChatRecordBox().getChildren().add(new MessageCarrier(true, message));
            }
        }
    }

    public void sendImg() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择图片");
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        if (file != null) {
            Socket socket = new Socket(PropertiesUtil.getValue("server.ip")
                    , Integer.parseInt(PropertiesUtil.getValue("client.file.upload.port")));
            FileInputStream fis = new FileInputStream(file);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            String fileName = System.currentTimeMillis() + file.getName();
            dos.writeUTF(fileName);
            String imgPath = FileUtil.getImgPath() + fileName;
            File localFile = new File(imgPath);
            FileOutputStream localSave = new FileOutputStream(localFile);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = fis.read(bytes)) != -1) {
                dos.write(bytes, 0, len);
                localSave.write(bytes, 0, len);
            }
            socket.shutdownOutput();
            fis.close();
            dos.close();
            localSave.close();
            String type = session.getCurrentChatWithType();
            Message message = new Message(user.getAccount(),
                    session.getCurrentChatWith(), Command.IMG_NAME + fileName, type);
            oos.writeObject(message);
            if (type.equals(Command.FRIEND)) {
                friendPaneHolder.getFriendPane(session.getCurrentChatWith()).getChatRecordBox().getChildren().add(new MessageCarrier(true, message));
            } else if (type.equals(Command.GROUP)) {
                groupPaneHolder.getGroupPane(session.getCurrentChatWith()).getChatRecordBox().getChildren().add(new MessageCarrier(true, message));
            }
            socket.close();
        }
    }
}

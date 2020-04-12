package com.jack.chat.controller;

import com.jack.chat.common.Session;
import com.jack.chat.pojo.User;
import com.jack.chat.service.UserService;
import com.jack.chat.service.imp.UserServiceImpl;
import com.jack.chat.util.EncryptUtil;
import com.jack.chat.util.FileUtil;
import com.jack.chat.util.PropertiesUtil;
import com.jack.transfer.LoginRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ResourceBundle;

/**
 * @author jack
 */
public class Login implements Initializable {

    public ImageView backGroundImg;
    public Pane loginPane;
    public TextField account;
    public PasswordField password;
    public Label minimize;
    public Label close;

    UserService userService = UserServiceImpl.getInstance();
    Session session = Session.getInstance();
    private Double offsetX;
    private Double offsetY;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginPane.setOnMousePressed(event -> {
            Window window = loginPane.getScene().getWindow();
            //             鼠标在屏幕中的坐标，    窗体在屏幕中的坐标
            this.offsetX = event.getScreenX() - window.getX();
            this.offsetY = event.getScreenY() - window.getY();
        });
        loginPane.setOnMouseDragged(event -> {
            Window window = loginPane.getScene().getWindow();
            //   新的鼠标位置-旧的鼠标位置+旧的窗体位置
            // = 鼠标的偏移量+旧的窗体位置
            window.setX(event.getScreenX() - this.offsetX);
            window.setY(event.getScreenY() - this.offsetY);
        });

        //关闭
        close.setOnMouseClicked(event -> {
            Platform.exit();
        });
        // 最小化
        minimize.setOnMouseClicked(event -> {
            Stage stage = (Stage) loginPane.getScene().getWindow();
            stage.setIconified(true);
        });
        loginPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        login();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void login() throws IOException {
        String accountText = account.getText();
        String passwordText = password.getText();
        passwordText = EncryptUtil.encrypt(passwordText);
        User user = userService.loginByAccountAndPassword(accountText, passwordText);
        if (user != null) {
            session.setUser(user);
            if (connectToServer()) {
                FileUtil.createDefaultFile();
                Stage loginStage = (Stage) loginPane.getScene().getWindow();
                Stage chatStage = new Stage();
                Parent chatWindow = FXMLLoader.load(getClass().getResource("/fxml/chatWindow2.0.fxml"));
                Scene scene = new Scene(chatWindow);
                scene.setFill(null);
                chatStage.setScene(scene);
                chatStage.initStyle(StageStyle.TRANSPARENT);
                chatStage.getIcons().add(new Image("img/logo.png"));
                chatStage.show();
                loginStage.close();
            }
        } else {
            new DialogPane();
        }
    }

    public boolean connectToServer() {
        boolean success = false;
        try {
            Socket socket = new Socket(PropertiesUtil.getValue("server.ip"), Integer.parseInt(PropertiesUtil.getValue(
                    "client.login.port")));
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            session.setOis(ois);
            session.setOos(oos);
            session.setSocket(socket);
            LoginRequest loginRequest = new LoginRequest(account.getText());
            oos.writeObject(loginRequest);
            success = true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    public void toRegister(ActionEvent actionEvent) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI(PropertiesUtil.getValue("register.url")));
    }
}

package com.jack.chat.controller;

import com.jack.chat.common.Session;
import com.jack.chat.pojo.User;
import com.jack.chat.service.UserService;
import com.jack.chat.service.imp.UserServiceImpl;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
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
                    login();
                }
            }
        });
    }

    public void login() {
        try {
            String accountText = account.getText();
            String passwordText = password.getText();
            User user = userService.loginByAccountAndPassword(accountText, passwordText);
            if (user != null) {
                session.setUser(user);
                if(connectToServer()) {
                    Stage chatStage = (Stage) loginPane.getScene().getWindow();
                    Parent chatWindow = FXMLLoader.load(getClass().getResource("/fxml/chatWindow2.0.fxml"));
                    Scene scene = new Scene(chatWindow);
                    scene.setFill(null);
                    chatStage.setScene(scene);
                    chatStage.show();
                }
            } else {
                System.out.println(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean connectToServer() { //
        boolean success = false;
        try {
            Socket createSocket = new Socket("127.0.0.1", 8888);
            DataInputStream dis = new DataInputStream(createSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(createSocket.getOutputStream());
            session.setDis(dis);
            session.setDos(dos);
            dos.writeUTF(session.getUser().getAccount());
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
}

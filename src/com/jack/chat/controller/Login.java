package com.jack.chat.controller;

import com.jack.chat.common.Session;
import com.jack.chat.common.SessionHolder;
import com.jack.chat.pojo.User;
import com.jack.chat.service.UserService;
import com.jack.chat.service.imp.UserServiceImp;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class Login implements Initializable {

    public ImageView backGroundImg;
    public Pane loginPane;
    public TextField account;
    public PasswordField password;

    UserService userService = new UserServiceImp();
    FlatAlert alert = new FlatAlert(Alert.AlertType.ERROR);
    Session session;
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
    }

    public void login() throws IOException {
        String accountText = account.getText();
        String passwordText = password.getText();
        User user = userService.loginByAccountAndPassword(accountText, passwordText);
        if (user != null) {
            session = new Session();
            session.setUser(user);
            if(connectToServer()) {
                SessionHolder sessionHolder = SessionHolder.getInstance();
                sessionHolder.setSession(session);
                Stage chatStage = (Stage) loginPane.getScene().getWindow();
                Parent chatWindow = FXMLLoader.load(getClass().getResource("../view/chatWindow.fxml"));
                Scene scene = new Scene(chatWindow);
                //new JMetro(scene, Style.LIGHT);
                chatStage.setScene(scene);
                chatStage.show();
            }
        } else {
            alert.setContentText("账号密码有误！");
            alert.showAndWait();
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setContentText("连接成功");
            alert.showAndWait();
            success = true;
        } catch (Exception e) {
            alert.setContentText("无法连接服务器");
            alert.showAndWait();
        }
        return success;
    }
}

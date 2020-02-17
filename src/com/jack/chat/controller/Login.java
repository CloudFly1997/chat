package com.jack.chat.controller;

import com.jack.chat.pojo.User;
import com.jack.chat.service.UserService;
import com.jack.chat.service.imp.UserServiceImp;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {


    public ImageView backGroundImg;
    public Pane loginPane;
    public TextField account;
    public PasswordField password;

    UserService userService = new UserServiceImp();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backGroundImg.setImage(new Image("img/login.png"));
    }

    public void login() throws IOException {
        String accountText = account.getText();
        String passwordText = password.getText();
        User user = userService.loginByAccountAndPassword(accountText, passwordText);
        if (user != null) {
            Stage chatStage = (Stage) loginPane.getScene().getWindow();
            Parent chatWindow = FXMLLoader.load(getClass().getResource("../view/chatWindow.fxml"));
            Scene scene = new Scene(chatWindow);
            chatStage.setScene(scene);
            chatStage.show();
        } else {
            //login failed
            System.out.println("i am here");

        }
    }
}

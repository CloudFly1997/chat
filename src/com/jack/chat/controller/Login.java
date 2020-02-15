package com.jack.chat.controller;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    public ImageView backGroundImg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backGroundImg.setImage(new Image("img/login.png"));
    }
}

package com.jack.chat.lunch;

import com.jack.chat.service.ReceiveMessageService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent chatWindow = FXMLLoader.load(getClass().getResource("../view/chatWindow.fxml"));
        Parent login = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
        primaryStage.setTitle("chat");
        Scene loginScene = new Scene(login);
        //Scene scene = new Scene(chatWindow);
        JMetro loginTheme = new JMetro(login, Style.LIGHT);
        //JMetro jMetro = new JMetro(chatWindow, Style.LIGHT);
        primaryStage.setScene(loginScene);
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

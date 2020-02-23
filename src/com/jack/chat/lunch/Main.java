package com.jack.chat.lunch;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent login = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
        primaryStage.setTitle("chat");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Scene loginScene = new Scene(login);
        new JMetro(loginScene, Style.LIGHT);
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

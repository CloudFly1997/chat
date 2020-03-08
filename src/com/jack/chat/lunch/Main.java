package com.jack.chat.lunch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author jack
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent login = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        primaryStage.setTitle("chat");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(login);
        scene.setFill(null);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("img/logo.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package com.jack.chat.lunch;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/2/29 22:37
 */

public class StageStyleTest extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UNIFIED);
        Pane pane = new Pane();
        pane.setPrefSize(500,500);
        pane.getChildren().add(new Button());
        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);

        primaryStage.setIconified(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

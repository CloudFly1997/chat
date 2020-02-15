package com.jack.chat.component;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class SelfPanel {
    public static Node dialogBox() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(605);
        scrollPane.setPrefHeight(324);
        VBox vBox = new VBox();
        vBox.prefWidthProperty().bind(scrollPane.prefWidthProperty());
        scrollPane.setContent(vBox);
        return scrollPane;
    }

    public static Node messageContainer() {
        return null;
    }
}

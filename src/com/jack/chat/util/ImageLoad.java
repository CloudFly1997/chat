package com.jack.chat.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/4/1 14:38
 */

public class ImageLoad {
    public static ImageView loadImg(String imgName) {
        ImageView imageView = new ImageView();
        Image image = null;
        String imgPath = System.getProperty("user.home") + "\\chat\\img\\" + imgName;
        String defaultPath = System.getProperty("user.home") + "\\chat\\img\\error.png";
        File file = new File(imgPath);
        if (file.exists()) {
            image = new Image("file:" + imgPath);
        } else {
            image = new Image("file:" + defaultPath);
        }

        imageView.setImage(image);
        imageView.setFitWidth(image.getWidth() * 0.4);
        imageView.setFitHeight(image.getHeight() * 0.4);
        imageView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
        return imageView;
    }
}
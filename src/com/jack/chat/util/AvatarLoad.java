package com.jack.chat.util;

import com.jack.chat.common.MainWindowHolder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/10 22:26
 */

public class AvatarLoad {
    public static void loadFriendAvatar(ImageView imageView, String name) {
        load(imageView, name, 100.0, 100.0);
    }

    public static void loadUserAvatar(ImageView imageView, String name) {
        load(imageView, name, 65.0, 65.0);
    }

    public static void loadProfileAvatar(ImageView imageView, String name) {
        load(imageView, name, 350.0, 230.0);
    }

    public static void loadAddFriendAvatar(ImageView imageView, String name) {
        load(imageView, name, 260.0, 260.0);
    }


    public static void loadSelfProfileAvatar(ImageView imageView, String name) {
        load(imageView, name, 240.0, 240.0);
        loadUserAvatar(MainWindowHolder.getInstance().getMainWindow().userAvatar,name);
    }

    private static void load(ImageView imageView, String name, Double requestedWidth, Double requestedHeight) {
        Image image = null;
        String imgPath = System.getProperty("user.home") + "\\chat\\avatar\\" + name + ".png";
        String defaultPath = System.getProperty("user.home") + "\\chat\\avatar\\default.png";
        try {
            InputStream in = null;
            Connection connection = DbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT avatar FROM user WHERE user_id " +
                    "= ?");
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                in = rs.getBinaryStream("avatar");
            }
            byte[] bytes = new byte[in.available()];
            OutputStream fileOutputStream = new FileOutputStream(imgPath);
            while (in.read(bytes) != -1) {
                fileOutputStream.write(bytes);
                fileOutputStream.flush();
            }
            in.close();
            fileOutputStream.close();
            image = new Image("file:" + imgPath, requestedWidth, requestedHeight, false, false);

        } catch (Exception e) {
            image = new Image("file:" + defaultPath, requestedWidth, requestedHeight, false, false);
        }
        imageView.setImage(image);
    }
}


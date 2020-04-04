package com.jack.chat.util;

import com.jack.chat.pojo.CommonIndividual;
import com.jack.chat.pojo.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileOutputStream;
import java.io.IOException;
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
    public static void loadFriendAvatar(ImageView imageView, CommonIndividual individual) {
        loadAvatar(imageView, individual, 100.0, 100.0);
    }

    public static void loadUserAvatar(ImageView imageView, CommonIndividual individual) {
        loadAvatar(imageView, individual, 65.0, 65.0);
    }

    public static void loadProfileAvatar(ImageView imageView, CommonIndividual individual) {
        loadAvatar(imageView, individual, 350.0, 230.0);
    }

    public static void loadAddFriendAvatar(ImageView imageView, CommonIndividual individual) {
        loadAvatar(imageView, individual, 260.0, 260.0);
    }

    public static void loadChatAvatar(ImageView imageView, CommonIndividual individual) {
        loadAvatar(imageView, individual, 60.0, 60.0);
    }

    public static void loadSelfProfileAvatar(ImageView imageView, CommonIndividual individual) {
        loadAvatar(imageView, individual, 240.0, 240.0);
    }

    public static void loadGroupPaneAvatar(ImageView imageView, Group group) {
        loadAvatar(imageView, group, 100.0, 100.0);
    }

    private static void load(ImageView imageView, String name, Double requestedWidth, Double requestedHeight) {
        Image image = null;
        String imgPath = System.getProperty("user.home") + "\\chat\\avatar\\" + name + ".png";
        String defaultPath = System.getProperty("user.home") + "\\chat\\avatar\\default.png";
        Connection connection = DbUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            InputStream in = null;

            preparedStatement = connection.prepareStatement("SELECT avatar FROM user WHERE user_id " +
                    "= ?");
            preparedStatement.setString(1, name);
            rs = preparedStatement.executeQuery();
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
        } finally {
            DbUtil.close(connection, rs, preparedStatement);
        }
        imageView.setImage(image);
    }

    public static void loadAvatar(ImageView imageView, CommonIndividual individual, Double requestedWidth,
                                  Double requestedHeight) {
        Image image = null;
        String imgPath = FileUtil.getAvatarPath() + individual.getId() + ".png";
        String defaultPath = FileUtil.getAvatarPath() + "default.png ";
        try {
            InputStream in = individual.getAvatarInputStream();
            if (in != null) {
                byte[] bytes = new byte[in.available()];
                OutputStream fileOutputStream = new FileOutputStream(imgPath);
                while (in.read(bytes) != -1) {
                    fileOutputStream.write(bytes);
                    fileOutputStream.flush();
                }
                in.close();
                fileOutputStream.close();
                image = new Image("file:" + imgPath, requestedWidth, requestedHeight, false, false);
            }
        } catch (IOException e) {
            image = new Image("file:" + defaultPath, requestedWidth, requestedHeight, false, false);
        }
        imageView.setImage(image);
    }
}


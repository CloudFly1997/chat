package com.jack.chat.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
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
    public static void load(ImageView imageView, String name) throws Exception {
        String imgPath = System.getProperty("user.home") + "\\chat\\avatar\\" + name + ".png";
        if (!new File(imgPath).exists()) {
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
        }
        Image image = new Image("file:"+imgPath);
        imageView.setImage(image);
    }
}

package com.jack.chat.util;

import com.jack.chat.pojo.CommonIndividual;
import com.jack.chat.pojo.Group;
import com.jack.chat.pojo.User;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/10 22:26
 */

public class AvatarUtil {

    public static void loadAvatarById(ImageView imageView, String id) {
        String imgPath = FileUtil.getAvatarPath() + id + ".png";
        Image image = new Image("file:" + imgPath);
        imageView.setImage(image);
    }

    public static void loadAvatar(ImageView imageView, CommonIndividual individual) {
        Image image = null;
        String imgPath = FileUtil.getAvatarPath() + individual.getId() + ".png";
        String defaultPath = FileUtil.getAvatarPath() + "default.png ";
        String sql = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        File imgFile = new File(imgPath);
        if (individual instanceof User) {
            sql = "select avatar from user where user_id = ?";
        } else if (individual instanceof Group) {
            sql = "select group_avatar from chat.`group` where group_id = ?";
        }
        try {
            //图片不存在则从数据库获取图片
            if (!imgFile.exists()) {
                connection = DbUtil.getConnection();
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, individual.getId());
                resultSet = preparedStatement.executeQuery();
                InputStream in = null;
                while (resultSet.next()) {
                    in = resultSet.getBinaryStream(1);
                    System.out.println(in.available());
                }
                if (in == null) {
                    in = AvatarUtil.class.getClassLoader().getResourceAsStream("img/logo.png");
                }
                byte[] bytes = new byte[1024];
                OutputStream fileOutputStream = new FileOutputStream(imgPath);
                int len = 0;
                while ((len = in.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, len);
                    fileOutputStream.flush();
                }
                fileOutputStream.close();
                in.close();
            }
            //如果文件存在直接使用
            image = new Image("file:" + imgPath);
        } catch (IOException | SQLException e) {
            image = new Image("file:" + defaultPath);
        } finally {
            DbUtil.close(connection, resultSet, preparedStatement);
        }
        imageView.setImage(image);
    }

    public static void loadAvatar(ImageView imageView, CommonIndividual individual, Double requestedWidth,
                                  Double requestedHeight) {
        Image image = null;
        String imgPath = FileUtil.getAvatarPath() + individual.getId() + ".png";
        String defaultPath = FileUtil.getAvatarPath() + "default.png ";
        String sql = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        File imgFile = new File(imgPath);
        if (individual instanceof User) {
            sql = "select avatar from user where user_id = ?";
        } else if (individual instanceof Group) {
            sql = "select group_avatar from user where group_id = ?";
        }
        try {
            //图片不存在则从数据库获取图片
            if (!imgFile.exists()) {
                connection = DbUtil.getConnection();
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, individual.getId());
                resultSet = preparedStatement.executeQuery();
                InputStream in = null;
                while (resultSet.next()) {
                    in = resultSet.getBinaryStream(1);
                    System.out.println(in.available());
                }
                if (in == null) {
                    in = AvatarUtil.class.getClassLoader().getResourceAsStream("img/logo.png");
                }
                byte[] bytes = new byte[1024];
                OutputStream fileOutputStream = new FileOutputStream(imgPath);
                int len = 0;
                while ((len = in.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, len);
                    fileOutputStream.flush();
                }
                fileOutputStream.close();
                in.close();
            }
            //如果文件存在直接使用
            image = new Image("file:" + imgPath, requestedWidth, requestedHeight, false, false);
        } catch (IOException | SQLException e) {
            image = new Image("file:" + defaultPath, requestedWidth, requestedHeight, false, false);
        } finally {
            DbUtil.close(connection, resultSet, preparedStatement);
        }
        imageView.setImage(image);
    }

    public static void changeAvatar(CommonIndividual individual,InputStream inputStream) {

        String imgPath = FileUtil.getAvatarPath() + individual.getId() + ".png";
        File file = new File(imgPath);
        if (file.exists()) {
            file.delete();
        }
        String sql = "";
        if (individual instanceof User) {
            sql = "update user set avatar = ? where user_id = ?";
        } else if (individual instanceof Group) {
            sql = "update group set group_avatar = ? group_id = ?";
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBinaryStream(1,inputStream,inputStream.available());
            preparedStatement.setString(2, individual.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(connection, null, preparedStatement);
        }
    }


}


package com.jack.chat.util;

import com.jack.chat.pojo.CommonIndividual;
import com.jack.chat.pojo.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;


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


    public static void loadAvatarById(ImageView imageView, String id) {
        String imgPath = FileUtil.getAvatarPath() + id + ".png";
        Image image = new Image("file:" + imgPath);
        imageView.setImage(image);
    }

    private static void loadAvatar(ImageView imageView, CommonIndividual individual, Double requestedWidth,
                                   Double requestedHeight) {
        Image image = null;
        String imgPath = FileUtil.getAvatarPath() + individual.getId() + ".png";
        String defaultPath = FileUtil.getAvatarPath() + "default.png ";
        File imgFile = new File(imgPath);
        InputStream in = individual.getAvatarInputStream();
        try {
            if (in == null) {
                in = AvatarLoad.class.getClassLoader().getResourceAsStream("img/logo.png");
            }
            //图片不存在则用流创建图片
            if (!imgFile.exists()) {
                byte[] bytes = new byte[in.available()];
                OutputStream fileOutputStream = new FileOutputStream(imgPath);
                while (in.read(bytes) != -1) {
                    fileOutputStream.write(bytes);
                    fileOutputStream.flush();
                }

                fileOutputStream.close();
            }
            image = new Image("file:" + imgPath, requestedWidth, requestedHeight, false, false);
        } catch (IOException e) {
            image = new Image("file:" + defaultPath, requestedWidth, requestedHeight, false, false);
        }
        imageView.setImage(image);
    }

    public static void changeAvatar(CommonIndividual individual) {
        String imgPath = FileUtil.getAvatarPath() + individual.getId() + ".png";
        FileInputStream in = (FileInputStream)individual.getAvatarInputStream();
        File file = new File(imgPath);

        if (file.exists()) {
            file.delete();
        }
        try {
            byte[] bytes = new byte[in.available()];
            OutputStream outputStream = new FileOutputStream(imgPath);
            int len = 0;
            while ((len = in.read(bytes) )!= -1) {
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


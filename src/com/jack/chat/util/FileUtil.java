package com.jack.chat.util;

import com.jack.chat.common.Session;

import java.io.*;
import java.net.Socket;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/4/3 14:24
 */

public class FileUtil {

    private static String parentPath;


    private static String imgPath;
    private static String avatarPath;
    private static String filePath;

    public static void createDefaultFile() {
        parentPath = System.getProperty("user.home") + PropertiesUtil.getValue("file.parent") + Session.getInstance().getUser().getAccount() + "/";
        imgPath = parentPath + PropertiesUtil.getValue("file.img.path");
        filePath = parentPath + PropertiesUtil.getValue("file.file.path");
        avatarPath = parentPath + PropertiesUtil.getValue("file.avatar.path");
        File imgFile = new File(imgPath);
        File fileFile = new File(filePath);
        File avatarFile = new File(avatarPath);
        if (!imgFile.exists()) {
            imgFile.mkdirs();
        }

        if (!fileFile.exists()) {
            fileFile.mkdirs();
        }

        if (!avatarFile.exists()) {
            avatarFile.mkdirs();
        }
    }

    public static String getParentPath() {
        return parentPath;
    }

    public static String getImgPath() {
        return imgPath;
    }

    public static String getAvatarPath() {
        return avatarPath;
    }

    public static String getFilePath() {
        return filePath;
    }


    public static void downloadImg(String imgName) {
        try {
            Socket socket = new Socket(PropertiesUtil.getValue("server.ip"),
                    Integer.parseInt(PropertiesUtil.getValue("client.file.download.port")));
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            String imgPath = getImgPath() + imgName;
            File file = new File(imgPath);
            dos.writeUTF(imgName);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len = 0;
            //往字节流里写图片数据
            while ((len = dis.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.close();
            dis.close();
            dos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAvatarFile() {
        File avatarParent = new File(avatarPath);
        File[] files = avatarParent.listFiles();
        for (File f :
                files) {
            if (f.isFile()) {
                f.delete();
            }
        }
    }
}

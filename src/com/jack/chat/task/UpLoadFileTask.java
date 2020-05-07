package com.jack.chat.task;

import com.jack.chat.util.FileUtil;
import com.jack.chat.util.PropertiesUtil;
import javafx.concurrent.Task;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/5/5 23:42
 */

public class UpLoadFileTask extends Task<Number> {

    File file;

    public UpLoadFileTask(File file) {
        this.file = file;
    }

    @Override
    protected void updateProgress(double workDone, double max) {
        super.updateProgress(workDone, max);
    }

    @Override
    protected void updateValue(Number value) {
        super.updateValue(value);
    }

    @Override
    protected Number call() throws Exception {
        Socket socket = new Socket(PropertiesUtil.getValue("server.ip")
                , Integer.parseInt(PropertiesUtil.getValue("client.file.upload.port")));
        FileInputStream fis = new FileInputStream(file);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        String fileName = System.currentTimeMillis() + file.getName();
        dos.writeUTF(fileName);
        String imgPath = FileUtil.getImgPath() + fileName;
        File localFile = new File(imgPath);
        FileOutputStream localSave = new FileOutputStream(localFile);
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = fis.read(bytes)) != -1) {
            dos.write(bytes, 0, len);
            localSave.write(bytes, 0, len);
        }
        socket.shutdownOutput();
        fis.close();
        dos.close();
        localSave.close();

        socket.close();
        return null;
    }
}

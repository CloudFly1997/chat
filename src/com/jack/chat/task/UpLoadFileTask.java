package com.jack.chat.task;

import com.jack.chat.common.Session;
import com.jack.chat.util.Command;
import com.jack.chat.util.FileUtil;
import com.jack.chat.util.PropertiesUtil;
import com.jack.transfer.Message;
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
    Message message;

    public UpLoadFileTask(Message message) {
        this.message = message;
        String path = message.getMessageContent().replace(Command.FILE_NAME, "");
        this.file = new File(path);
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
        //将文件名设置为唯一
        String fileName = System.currentTimeMillis() + Command.SPLIT_CODE + file.getName();
        //告知服务器上传的文件名
        dos.writeUTF(fileName);

        //将文件复制到程序文件夹
        String imgPath = FileUtil.getFilePath() + fileName;
        File localFile = new File(imgPath);
        FileOutputStream localSave = new FileOutputStream(localFile);

        byte[] bytes = new byte[1024];
        double totalSize = fis.available();
        double sum = 0;
        int len = 0;
        while ((len = fis.read(bytes)) != -1) {
            dos.write(bytes, 0, len);
            sum += len;
            this.updateProgress(sum, totalSize);
            localSave.write(bytes, 0, len);
        }
        socket.shutdownOutput();
        fis.close();
        dos.close();
        localSave.close();
        socket.close();
        message.setMessageContent(Command.FILE_NAME + fileName + Command.SPLIT_CODE + totalSize);
        Session.getInstance().getOos().writeObject(message);
        return sum;
    }
}

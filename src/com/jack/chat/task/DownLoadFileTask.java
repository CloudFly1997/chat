package com.jack.chat.task;

import com.jack.chat.util.Command;
import com.jack.chat.util.FileUtil;
import com.jack.chat.util.MessageHandle;
import com.jack.chat.util.PropertiesUtil;
import com.jack.transfer.Message;
import javafx.concurrent.Task;

import java.io.*;
import java.net.Socket;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/5/7 16:22
 */

public class DownLoadFileTask extends Task<Number> {
    Message message;

    public DownLoadFileTask(Message message) {
        this.message = message;
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
        Socket socket = new Socket(PropertiesUtil.getValue("server.ip"),
                Integer.parseInt(PropertiesUtil.getValue("client.file.download.port")));

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());


        String content = message.getMessageContent().replace(Command.FILE_NAME, "");


        //需要去服务器下载的文件名
        String needDownLoadFileName = MessageHandle.getNeedDownLoadFileNameFromMessageContent(content);
        //真实的文件名
        String fileName = MessageHandle.getFileNameFromMessageContent(content);
        //需要下载文件的总大小
        double totalSize = Double.parseDouble(MessageHandle.getFileSizeFromMessageContent(content));

        //本地存放路径
        String imgPath = FileUtil.getFilePath() + fileName;
        File file = new File(imgPath);

        //告知服务器需要下载哪个文件
        dos.writeUTF(needDownLoadFileName);
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buf = new byte[Integer.parseInt(PropertiesUtil.getValue("file.download.speed"))];
        double sum = 0;
        int len = 0;
        //往字节流里写图片数据
        while ((len = dis.read(buf)) != -1) {
            fos.write(buf, 0, len);
            sum += len;
            this.updateProgress(sum, totalSize);
        }
        fos.close();
        dis.close();
        dos.close();
        socket.close();

        return sum;
    }
}

package com.jack.chat.component;

import com.jack.chat.task.DownLoadFileTask;
import com.jack.chat.util.Command;
import com.jack.chat.util.FileUtil;
import com.jack.chat.util.MessageHandle;
import com.jack.transfer.Message;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/5/6 0:27
 */

public class ReceiveFileMessagePane extends Pane {
    public Label fileName, fileSize;
    public ProgressBar progressBar;
    public Pane root;


    public ReceiveFileMessagePane(Message message) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/fileMessagePane.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
            String content = message.getMessageContent().replace(Command.FILE_NAME, "");
            fileName.setText(MessageHandle.getFileNameFromMessageContent(content));
            double b = Double.parseDouble(MessageHandle.getFileSizeFromMessageContent(content));
            String size = String.format("%.2f", b / 1024 / 1024);
            fileSize.setText(size + "M");
            downLoad(message);
            root.setOnMouseClicked(event ->  {
                if (event.getClickCount() == 2) {
                    try {
                        Desktop.getDesktop().open(new File(FileUtil.getFilePath()+fileName.getText()));
                    } catch (IOException e) {
                        //文件损坏移动会出错
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void downLoad(Message message) {
        DownLoadFileTask task = new DownLoadFileTask(message);
        task.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                progressBar.setProgress(newValue.doubleValue());
            }
        });
        new Thread(task).start();
    }
}

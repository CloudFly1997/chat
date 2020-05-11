package com.jack.chat.component;

import com.jack.chat.task.UpLoadFileTask;
import com.jack.chat.util.Command;
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

public class SendFileMessagePane extends Pane {
    public Label fileName,fileSize;
    public ProgressBar progressBar;
    File file;
    public Pane root;


    public SendFileMessagePane(Message message) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/fileMessagePane.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
            String path = message.getMessageContent().replace(Command.FILE_NAME,"");
            file = new File(path);
            fileName.setText(file.getName());
            String size = String.format("%.2f",file.length()/1024.0/1024.0);
            fileSize.setText(size+"M");
            upLoad(message);
            root.setOnMouseClicked(event ->  {
                if (event.getClickCount() == 2) {
                    try {
                        Desktop.getDesktop().open(file);
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

    public void upLoad(Message message) {
        UpLoadFileTask task = new UpLoadFileTask(message);
        task.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                progressBar.setProgress(newValue.doubleValue());
            }
        });
        new Thread(task).start();
    }

}

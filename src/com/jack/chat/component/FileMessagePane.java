package com.jack.chat.component;

import com.jack.chat.task.UpLoadFileTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/5/6 0:27
 */

public class FileMessagePane extends Pane {
    public Label fileName,fileSize;
    public ProgressBar progressBar;


    public FileMessagePane(String info) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/fileMessagePane.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
            fileName.setText(info.split("/")[1]);
            String size = String.format("%.1f",Long.parseLong(info.split("/")[2])/1024.0/1024.0);
            fileSize.setText(size+"M");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void upLoad(File file) {
        UpLoadFileTask task = new UpLoadFileTask(file);
        task.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                progressBar.setProgress(newValue.doubleValue());
            }
        });
    }

    public void downLoad() {

    }
}

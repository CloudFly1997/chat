package com.jack.chat.service;

import com.jack.chat.util.MessageUtil;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.DataInputStream;

public class ReceiveMessageService extends ScheduledService<String> {

    VBox vbox;
    DataInputStream dataInputStream;

    public ReceiveMessageService(VBox vBox, DataInputStream dataInputStream) {
        this.vbox = vBox;
        this.dataInputStream = dataInputStream;
        this.setPeriod(Duration.seconds(0));
    }


    @Override
    protected void executeTask(Task<String> task) {
        super.executeTask(task);
    }

    @Override
    protected Task<String> createTask() {
        Task<String> task = new Task<String>() {
            @Override
            protected void updateValue(String value) {
                super.updateValue(value);
                //playSound.playSoundWhenReceiveMessage();
                System.out.println(value);
                vbox.getChildren().add(MessageUtil.assembleMessage(value,false));
            }

            @Override
            protected String call() throws Exception {
                String getMessageFromServer = dataInputStream.readUTF();
                return getMessageFromServer;
            }
        };
        return task;
    }
}

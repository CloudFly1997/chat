package com.jack.chat.service;

import com.jack.chat.common.FriendPaneHolder;
import com.jack.chat.common.SessionHolder;
import com.jack.chat.component.MessageCarrier;
import com.jack.chat.util.MessageHandle;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;


/**
 * 后台接收消息服务
 * @author jack
 */
public class ReceiveMessageService extends ScheduledService<String> {

    private FriendPaneHolder friendPaneHolder = FriendPaneHolder.getInstance();

    public ReceiveMessageService() {
        this.setPeriod(Duration.seconds(0));
    }


    @Override
    protected void executeTask(Task<String> task) {
        super.executeTask(task);
    }

    @Override
    protected Task<String> createTask() {
        Task<String> task = new Task<String>() {
            /**
             * 接收到消息，更新chatPane
             * @param value
             */
            @Override
            protected void updateValue(String value) {
                super.updateValue(value);
                System.out.println(value);
                String from = MessageHandle.getFrom(value);
                friendPaneHolder.getFriendPane(from).receiveMessage(new MessageCarrier(MessageHandle.getContent(value)));
            }

            @Override
            protected String call() throws Exception {
                String getMessageFromServer = SessionHolder.getInstance().getSession().getDis().readUTF();
                return getMessageFromServer;
            }
        };
        return task;
    }
}

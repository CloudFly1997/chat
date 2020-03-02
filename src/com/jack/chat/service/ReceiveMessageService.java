package com.jack.chat.service;

import com.jack.chat.common.ChatPaneHolder;
import com.jack.chat.common.FriendPaneHolder;
import com.jack.chat.common.SessionHolder;
import com.jack.chat.component.ChatPane;
import com.jack.chat.component.MessageCarrier;
import com.jack.chat.util.MessageHandle;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;


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
                ChatPane chatPane = ChatPaneHolder.getInstance().getChatPane(from);
                friendPaneHolder.getFriendPane(from).getChatRecordBox().getChildren().add(new MessageCarrier(MessageHandle.getContent(value)));
                //如果当前聊天面板为空
                if (chatPane == null) {
                    friendPaneHolder.getFriendPane(from).messageOffer(MessageHandle.getContent(value));
                } else {
                    chatPane.getVBox().getChildren().add(new MessageCarrier(MessageHandle.getContent(value)));
                }
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

package com.jack.chat.thread;

import com.jack.chat.common.FriendPaneHolder;
import com.jack.chat.common.MainWindowHolder;
import com.jack.chat.common.Session;
import com.jack.chat.component.AddFriendDialog;
import com.jack.chat.component.FriendPane;
import com.jack.chat.component.MessageCarrier;
import com.jack.chat.service.imp.UserServiceImpl;
import com.jack.chat.util.CommandHandle;
import com.jack.chat.util.MessageHandle;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;


/**
 * 后台接收消息服务
 *
 * @author jack
 */
public class ReceiveMessageService extends ScheduledService<String> {

    private FriendPaneHolder friendPaneHolder = FriendPaneHolder.getInstance();
    private FriendPane SystemNotifier = friendPaneHolder.getFriendPane("1");
    public ReceiveMessageService() {
        //设置轮询间隔
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
             * 接收到消息
             * @param value
             */
            @Override
            protected void updateValue(String value) {
                super.updateValue(value);
                System.out.println(value);
                String type = MessageHandle.getMessageType(value);
                switch (type) {
                    case "[TXT]":
                        String from = MessageHandle.getFrom(value);
                        friendPaneHolder.getFriendPane(from).receiveMessage(new MessageCarrier(MessageHandle.getContent(value)));
                        break;
                    case "[ADD_FRIEND]":
                        SystemNotifier.receiveMessage(new AddFriendDialog(CommandHandle.getWhoAddMe(value)));
                        break;
                    case "[AGREE_ADD_FRIEND]":
                        String account = CommandHandle.getWhoAddMe(value);
                        FriendPane newFriendPane =
                                new FriendPane(UserServiceImpl.getInstance().queryUserByAccount(account));
                        friendPaneHolder.addFriendPane(account, newFriendPane);
                        MainWindowHolder.getInstance().getMainWindow().friendList.getChildren().add(1, newFriendPane);
                        break;
                    case "[DELETE_FRIEND]":

                        break;
                    case "[]":
                        break;
                    default:
                }

            }


            @Override
            protected String call() throws Exception {
                String getMessageFromServer = Session.getInstance().getDis().readUTF();
                return getMessageFromServer;
            }
        };
        return task;
    }
}

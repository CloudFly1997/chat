package com.jack.chat.thread;

import com.jack.chat.common.FriendPaneHolder;
import com.jack.chat.common.MainWindowHolder;
import com.jack.chat.common.Session;
import com.jack.chat.component.AddFriendDialog;
import com.jack.chat.component.FriendPane;
import com.jack.chat.component.MessageCarrier;
import com.jack.chat.service.imp.UserServiceImpl;
import com.jack.transfer.Message;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

import java.io.File;


/**
 * 后台接收消息服务
 *
 * @author jack
 */
public class ReceiveMessageService extends ScheduledService<Object> {

    private FriendPaneHolder friendPaneHolder = FriendPaneHolder.getInstance();
    private FriendPane SystemNotifier = friendPaneHolder.getFriendPane("1");

    public ReceiveMessageService() {
        //设置轮询间隔
        this.setPeriod(Duration.seconds(0));
    }

    @Override
    protected void executeTask(Task<Object> task) {
        super.executeTask(task);
    }

    @Override
    protected Task<Object> createTask() {
        Task<Object> task = new Task<Object>() {
            /**
             * 接收到消息
             * @param obj
             */
            @Override
            protected void updateValue(Object obj) {
                super.updateValue(obj);
                System.out.println(obj);
                if (obj instanceof Message) {
                    Message message = (Message) obj;
                    switch (message.getType()) {
                        case "[TXT]":
                            String from = message.getFromUser();
                            friendPaneHolder.getFriendPane(from).receiveMessage(new MessageCarrier(message));
                            break;
                        case "[ADD_FRIEND]":
                            SystemNotifier.receiveMessage(new AddFriendDialog(message.getFromUser()));
                            break;
                        case "[AGREE_ADD_FRIEND]":
                            String account = message.getFromUser();
                            FriendPane newFriendPane =
                                    new FriendPane(UserServiceImpl.getInstance().queryUserByAccount(account));
                            friendPaneHolder.addFriendPane(account, newFriendPane);
                            MainWindowHolder.getInstance().getMainWindow().friendListBox.getChildren().add(1,
                                    newFriendPane);
                            break;
                        case "[DELETE_FRIEND]":
                            MainWindowHolder.getInstance().getMainWindow().friendListBox.getChildren().remove(
                                    FriendPaneHolder.getInstance().getFriendPane(message.getFromUser()));
                            FriendPaneHolder.getInstance().remove(message.getFromUser());
                            break;
                        case "[]":
                            break;
                        default:
                    }
                } else if (obj instanceof File) {
                    File file = (File)obj;
                    String name = file.getName();
                    System.out.println(name);
                }

            }

            @Override
            protected Object call() throws Exception {
                Object receiveObject = Session.getInstance().getOis().readObject();
                return receiveObject;
            }
        };
        return task;
    }
}

package com.jack.chat.thread;

import com.jack.chat.common.FriendPaneHolder;
import com.jack.chat.common.GroupPaneHolder;
import com.jack.chat.common.MainWindowHolder;
import com.jack.chat.common.Session;
import com.jack.chat.component.*;
import com.jack.chat.service.imp.GroupServiceImpl;
import com.jack.chat.service.imp.UserServiceImpl;
import com.jack.chat.util.Command;
import com.jack.transfer.Message;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;


/**
 * 后台接收消息服务
 *
 * @author jack
 */
public class ReceiveMessageService extends ScheduledService<Object> {

    private FriendPaneHolder friendPaneHolder = FriendPaneHolder.getInstance();
    private GroupPaneHolder groupPaneHolder = GroupPaneHolder.getInstance();
    private NotifyPane SystemNotifier =
            (NotifyPane) MainWindowHolder.getInstance().getMainWindow().notifyBox.getChildren().get(0);
    //private ExecutorService exe = Executors.newFixedThreadPool(50);

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
                    Thread downloadThread = null;

                    switch (message.getType()) {
                        case Command.FRIEND:
                            String from = message.getFromUser();
                            friendPaneHolder.getFriendPane(from).receiveMessage(new MessageCarrier(message));
                            break;
                        case Command.GROUP:
                            String to = message.getToUser();
                            groupPaneHolder.getGroupPane(to).receiveMessage(new MessageCarrier(message));
                            break;
                        case Command.ADD_FRIEND:
                        case Command.APPLY_JOIN_GROUP:
                            SystemNotifier.receiveMessage(new NotifyDialog(message));
                            break;
                        case Command.AGREE_ADD_FRIEND:
                            String account = message.getFromUser();
                            FriendPane newFriendPane =
                                    new FriendPane(UserServiceImpl.getInstance().queryUserByAccount(account));
                            friendPaneHolder.addFriendPane(account, newFriendPane);
                            MainWindowHolder.getInstance().getMainWindow().friendListBox.getChildren().add(0,
                                    newFriendPane);
                            break;
                        case Command.DELETE_FRIEND:
                            MainWindowHolder.getInstance().getMainWindow().friendListBox.getChildren().remove(
                                    FriendPaneHolder.getInstance().getFriendPane(message.getFromUser()));
                            FriendPaneHolder.getInstance().remove(message.getFromUser());
                            break;
                        case Command.AGREE_JOIN_GROUP:
                            GroupPane newGroupPane =
                                    new GroupPane(GroupServiceImpl.getInstance().queryById(message.getFromUser()));
                            GroupPaneHolder.getInstance().addGroupPane(message.getFromUser(),newGroupPane);
                            MainWindowHolder.getInstance().getMainWindow().groupListBox.getChildren().add(0,
                                    newGroupPane);
                            break;
                        case Command.IMG_NAME:

                            break;
                        default:
                    }
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

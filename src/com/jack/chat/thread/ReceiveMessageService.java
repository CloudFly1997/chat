package com.jack.chat.thread;

import com.jack.chat.common.FriendPaneHolder;
import com.jack.chat.common.GroupPaneHolder;
import com.jack.chat.common.MainWindowHolder;
import com.jack.chat.common.Session;
import com.jack.chat.component.AddFriendDialog;
import com.jack.chat.component.FriendPane;
import com.jack.chat.component.MessageCarrier;
import com.jack.chat.service.imp.UserServiceImpl;
import com.jack.chat.util.Command;
import com.jack.chat.util.PropertiesUtil;
import com.jack.transfer.Message;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

import java.io.*;
import java.net.Socket;


/**
 * 后台接收消息服务
 *
 * @author jack
 */
public class ReceiveMessageService extends ScheduledService<Object> {

    private FriendPaneHolder friendPaneHolder = FriendPaneHolder.getInstance();
    private GroupPaneHolder groupPaneHolder = GroupPaneHolder.getInstance();
    private FriendPane SystemNotifier = friendPaneHolder.getFriendPane("1");
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

                            if (message.getMessageContent().startsWith(Command.IMG_NAME)) {
                                downloadThread = new Thread(() -> {
                                    downloadImg(message.getMessageContent().replace(Command.IMG_NAME, ""));
                                });
                                downloadThread.start();
                            }
                            String from = message.getFromUser();
                            friendPaneHolder.getFriendPane(from).receiveMessage(new MessageCarrier(message));


                            break;
                        case Command.GROUP:
                            if (message.getMessageContent().startsWith(Command.IMG_NAME)) {
                                downloadThread = new Thread(() -> {
                                    downloadImg(message.getMessageContent().replace(Command.IMG_NAME, ""));
                                });
                                downloadThread.start();
                            }
                            String to = message.getToUser();
                            groupPaneHolder.getGroupPane(to).receiveMessage(new MessageCarrier(message));

                            break;
                        case Command.ADD_FRIEND:
                            SystemNotifier.receiveMessage(new AddFriendDialog(message.getFromUser()));
                            break;
                        case Command.AGREE_ADD_FRIEND:
                            String account = message.getFromUser();
                            FriendPane newFriendPane =
                                    new FriendPane(UserServiceImpl.getInstance().queryUserByAccount(account));
                            friendPaneHolder.addFriendPane(account, newFriendPane);
                            MainWindowHolder.getInstance().getMainWindow().friendListBox.getChildren().add(1,
                                    newFriendPane);
                            break;
                        case Command.DELETE_FRIEND:
                            MainWindowHolder.getInstance().getMainWindow().friendListBox.getChildren().remove(
                                    FriendPaneHolder.getInstance().getFriendPane(message.getFromUser()));
                            FriendPaneHolder.getInstance().remove(message.getFromUser());
                            break;
                        case Command.IMG_NAME:

                            break;
                        default:
                    }
                } else if (obj instanceof File) {
                    File file = (File) obj;
                    String name = file.getName();
                    System.out.println(name);
                }

            }

            public void downloadImg(String imgName) {
                try {
                    Socket socket = new Socket(PropertiesUtil.getValue("server.ip"),
                            Integer.parseInt(PropertiesUtil.getValue("client.file.download.port")));
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    String imgPath = System.getProperty("user.home") + "\\chat\\reimg\\" + imgName;
                    File file = new File(imgPath);
                    dos.writeUTF(imgName);
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] buf = new byte[1024];
                    int len = 0;
                    //往字节流里写图片数据
                    while ((len = dis.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.close();
                    dis.close();
                    dos.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
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

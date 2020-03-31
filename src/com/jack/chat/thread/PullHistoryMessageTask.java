package com.jack.chat.thread;

import com.jack.chat.common.Session;
import com.jack.chat.component.FriendPane;
import com.jack.chat.component.GroupPane;
import com.jack.chat.service.MessageService;
import com.jack.chat.service.imp.MessageServiceImpl;
import com.jack.transfer.Message;
import javafx.concurrent.Task;
import javafx.scene.layout.Pane;

import java.util.List;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/30 23:58
 */

public class PullHistoryMessageTask extends Task<List<Message>> {
    Pane pane;
    MessageService messageService = new MessageServiceImpl();

    public PullHistoryMessageTask(FriendPane friendPane) {
        this.pane = friendPane;
    }

    @Override
    protected void updateValue(List<Message> value) {
        super.updateValue(value);
        for (Message message :
                value) {

/*            if (message.getFromUser().equals(Session.getInstance().getUser().getAccount())) {
                friendPane.getChatRecordBox().getChildren().add(new MessageCarrier(true, message));
            } else {
                friendPane.getChatRecordBox().getChildren().add(new MessageCarrier(message));
                if (!message.isRead()) {
                    friendPane.unReadMessageCountAdd();
                }
            }*/
        }
    }

    @Override
    protected List<Message> call() throws Exception {
        if (pane instanceof FriendPane) {
            return messageService.queryHistoryMessage(((FriendPane) pane).getUser(), Session.getInstance().getUser());
        } else {
            return messageService.queryHistoryMessage(((GroupPane) pane).getGroup(), Session.getInstance().getUser());
        }

    }
}

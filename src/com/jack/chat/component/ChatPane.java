package com.jack.chat.component;

import com.jack.chat.pojo.User;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
/*<Pane fx:id="chatPane" layoutX="146.0" layoutY="81.0" prefHeight="515.0" prefWidth="748.0" visible="true" AnchorPane.bottomAnchor="0.0" AnchorPane.rightAnchor="0.0">
<children>
<TextArea fx:id="messageEditArea" layoutY="318.0" prefHeight="194.0" prefWidth="605.0" wrapText="true">
<font>
<Font name="YouYuan" size="16.0" />
</font>
</TextArea>
<Button fx:id="messageSendButton" layoutX="516.0" layoutY="451.0" mnemonicParsing="false" onAction="#sendMessage" prefHeight="30.0" prefWidth="77.0" styleClass="sendButton" text="发送">
<font>
<Font name="Microsoft YaHei Bold" size="12.0" />
</font>
</Button>
<ScrollPane fx:id="scrollPanel" layoutY="1.0" prefHeight="303.0" prefWidth="597.0" styleClass="messageShowArea">
<content>
<VBox fx:id="messageShowArea" prefHeight="303.0" prefWidth="575.0" spacing="10" styleClass="messageCell" />
</content>
</ScrollPane>
</children>
</Pane>*/
public class ChatPane extends Pane {
    String account;
    public ChatPane(User user) {
        this.account = user.getAccount();
        init();
    }
    public void init() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(600,305);
        scrollPane.setFitToWidth(true);
        VBox vBox = new VBox(10);
        vBox.setPrefSize(600,305);
        scrollPane.setContent(vBox);
    }
}

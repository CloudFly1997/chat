package com.jack.chat.component;

import com.jack.chat.pojo.User;
import com.jack.chat.util.AvatarUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/10 12:28
 */

public class FriendProfilePane extends Pane {
    public TextField id, nickName, remark, gender, birthday, email, phone, address;
    private User user;
    public ImageView avatar;

    public FriendProfilePane(User user) {
        this.user = user;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/friendProfile.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
            AvatarUtil.loadAvatar(avatar,user,350.0,235.0);
            id.setText(user.getAccount());
            nickName.setText(user.getNickName());
            remark.setText(user.getFriend_remark());
            gender.setText(user.getGender());
            birthday.setText(user.getBirthday());
            email.setText(user.getEmail());
            phone.setText(user.getPhoneNumber());
            address.setText(user.getAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void show() {
        Stage stage = new Stage();
        Scene scene = new Scene(this);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }
}

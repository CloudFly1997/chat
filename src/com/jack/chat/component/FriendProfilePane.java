package com.jack.chat.component;

import com.jack.chat.pojo.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/10 12:28
 */

public class FriendProfilePane extends ProfilePane{
    public TextField id, nickName, remark, gender, birthday, email, phone, address;
    private User user;

    public FriendProfilePane(User user) {
        this.user = user;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/friendProfile.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        id.setText(user.getAccount());
        nickName.setText(user.getNickName());
        remark.setText(user.getFriend_remark());
        gender.setText(user.getGender());
        birthday.setText(user.getBirthday());
        email.setText(user.getEmail());
        phone.setText(user.getPhoneNumber());
        address.setText(user.getAddress());
    }

}

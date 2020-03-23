package com.jack.chat.component;

import com.jack.chat.pojo.User;
import com.jack.chat.util.AvatarLoad;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/10 12:28
 */

public class FriendProfilePane extends ProfilePane{
    public TextField id, nickName, remark, gender, birthday, email, phone, address;
    private User user;
    public ImageView avatar;

    public FriendProfilePane(User user) {
        this.user = user;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/friendProfile.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.maximizedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                    System.out.println(1);
                }
            });
            AvatarLoad.loadProfileAvatar(avatar,user.getAccount());
            id.setText(user.getAccount());
            nickName.setText(user.getNickName());
            remark.setText(user.getFriend_remark());
            gender.setText(user.getGender());
            birthday.setText(user.getBirthday());
            email.setText(user.getEmail());
            phone.setText(user.getPhoneNumber());
            address.setText(user.getAddress());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

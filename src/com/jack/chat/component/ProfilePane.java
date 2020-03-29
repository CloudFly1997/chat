package com.jack.chat.component;

import com.jack.chat.pojo.User;
import com.jack.chat.util.AvatarLoad;
import com.jack.chat.util.DbUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/10 12:28
 */

public class ProfilePane extends Pane {
    public Pane root;
    public TextField account, nickName, phone, email, address;
    public RadioButton male, female;
    public ToggleGroup genderGroup;
    public ImageView avatar;
    public DatePicker birthday;
    public TextArea signature;

    public ProfilePane(User user) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/profilePane.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
            account.setText(user.getAccount());
            nickName.setText(user.getNickName());
            phone.setText(user.getPhoneNumber());
            email.setText(user.getEmail());
            address.setText(user.getAddress());
            if ("男".equals(user.getGender())) {
                male.setSelected(true);
            } else {
                female.setSelected(true);
            }
            String[] date = user.getBirthday().split("-");
            if (date.length == 3) {
                birthday.setValue(LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]),
                        Integer.parseInt(date[2])));
            }
            AvatarLoad.loadSelfProfileAvatar(avatar, user.getAccount());
            signature.setText(user.getSignature());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {

    }

    public void chooseAvatar() throws IOException, SQLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),

                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("IMG", "*.img*"));

        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        if (file != null) {
            InputStream in = new FileInputStream(file);
            Connection conn = DbUtil.getConnection();
            String sql = "UPDATE user SET avatar = ? WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps = conn.prepareStatement(sql);

            ps.setBinaryStream(1, in, in.available());
            ps.setString(2, account.getText());
            ps.executeUpdate();
            AvatarLoad.loadSelfProfileAvatar(avatar, account.getText());
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

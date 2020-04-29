package com.jack.chat.component;

import com.jack.chat.common.MainWindowHolder;
import com.jack.chat.controller.MainWindow;
import com.jack.chat.pojo.User;
import com.jack.chat.service.UserService;
import com.jack.chat.service.imp.UserServiceImpl;
import com.jack.chat.util.AvatarUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
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
    public User user;
    private InputStream inputStream;
    MainWindow mainWindow;


    public ProfilePane(User user) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/profilePane.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
            this.user = user;
            account.setText(user.getAccount());
            nickName.setText(user.getNickName());
            phone.setText(user.getPhoneNumber());
            email.setText(user.getEmail());
            address.setText(user.getAddress());
            male.setUserData("男");
            female.setUserData("女");
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
            AvatarUtil.loadAvatar(avatar, user);
            signature.setText(user.getSignature());
            mainWindow = MainWindowHolder.getInstance().getMainWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() throws IOException {
        user.setNickName(nickName.getText());
        user.setBirthday(birthday.getValue().toString());
        user.setGender(genderGroup.getSelectedToggle().getUserData().toString());
        user.setEmail(email.getText());
        user.setPhoneNumber(phone.getText());
        user.setAddress(address.getText());
        user.setSignature(signature.getText());
        UserService userService = UserServiceImpl.getInstance();
        userService.updateUser(user);
        mainWindow.userName.setText(user.getNickName()==null?user.getAccount():user.getNickName());
    }

    public void chooseAvatar() throws IOException, SQLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.jpg*", "*.png", "*.img*", "*.bmp", "*.jpeg"));
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        if (file != null) {
            inputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            user.setAvatar(bufferedInputStream);
            Image image = new Image("file:" + file.getAbsolutePath());
            avatar.setImage(image);
            AvatarUtil.changeAvatar(user,inputStream);//删除缓存头像
            AvatarUtil.loadAvatar(mainWindow.userAvatar, user);//刷新头像
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

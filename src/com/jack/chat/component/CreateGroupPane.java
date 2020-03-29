package com.jack.chat.component;

import com.jack.chat.common.Session;
import com.jack.chat.pojo.Group;
import com.jack.chat.service.imp.GroupServiceImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/28 13:57
 */

public class CreateGroupPane extends Pane {
    public Pane root;
    public TextField groupAccount, groupName;
    public TextArea groupIntroduce;
    public ImageView groupAvatar;
    public InputStream inputStream;
    public Label occupiedTip;

    public CreateGroupPane() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/createGroupPane.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        Stage stage = new Stage();
        Scene scene = new Scene(this);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void uploadAvatar() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),

                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("IMG", "*.img*"));

        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        if (file != null) {
            groupAvatar.setImage(new Image("file:" + file.getPath()));
            this.inputStream = new FileInputStream(file);
        }
    }

    public void create() {
        String groupAccount = this.groupAccount.getText();
        String groupName = this.groupName.getText();
        String groupIntroduce = this.groupIntroduce.getText();
        Group group = new Group(groupAccount, groupName, groupIntroduce, inputStream,
                Session.getInstance().getUser().getAccount());
        GroupServiceImpl.getInstance().create(group);
        System.out.println("创建成功！");
    }
}

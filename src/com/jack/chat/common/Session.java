package com.jack.chat.common;

import com.jack.chat.controller.MainWindow;
import com.jack.chat.pojo.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author jackIn
 */
public class Session {
    private User user;
    private boolean state;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private MainWindow mainWindow;
    private static Session session = new Session();
    private Socket socket;

    private Session() {

    }

    public static Session getInstance() {
        return session;
    }

    public boolean getState() {
        return state;
    }

    public User getUser() {
        return user;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }
}

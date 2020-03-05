package com.jack.chat.common;

import com.jack.chat.pojo.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * @author jack
 */
public class Session {
    private User user;
    private boolean state;
    private DataInputStream dis;
    private DataOutputStream dos;
    private static Session session = new Session();
    private Session() {

    }
    public static Session getInstance(){
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

    public DataInputStream getDis() {
        return dis;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public void setDis(DataInputStream dis) {
        this.dis = dis;
    }

    public void setDos(DataOutputStream dos) {
        this.dos = dos;
    }
}

package com.jack.transfer;

import java.io.Serializable;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/26 1:01
 */

public class LoginRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String user;

    public LoginRequest(String user) {
        this.user = user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }
}

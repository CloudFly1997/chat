package com.jack.chat.common;

public class SessionHolder {
    Session session;
    public static SessionHolder sessionHolder = null;

    private SessionHolder() {

    }

    public static SessionHolder getInstance() {
        if (sessionHolder == null) {
            sessionHolder = new SessionHolder();
        }
        return sessionHolder;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}

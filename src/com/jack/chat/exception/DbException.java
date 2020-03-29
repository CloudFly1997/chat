package com.jack.chat.exception;


/**
 * @author jack
 */
public class DbException extends RuntimeException {

    private static final long serialVersionUID = 5100244053468267012L;

    public DbException(Exception e) {
        e.printStackTrace();
    }
}

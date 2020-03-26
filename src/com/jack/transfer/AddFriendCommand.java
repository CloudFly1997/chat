package com.jack.transfer;

import java.io.Serializable;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/25 22:56
 */

public class AddFriendCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;

    private String from;

    private String to;
}

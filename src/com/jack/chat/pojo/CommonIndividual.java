package com.jack.chat.pojo;

import java.io.BufferedInputStream;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/30 0:19
 */

public  interface CommonIndividual {
    /**
     * 获取头像流
     * @return
     */
    BufferedInputStream getAvatarInputStream();

    /**
     * 获取标识符
     * @return
     */
    String getId();
}

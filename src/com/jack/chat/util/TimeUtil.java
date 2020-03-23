package com.jack.chat.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/21 12:21
 */

public class TimeUtil {
    public static String getNowTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String date = dateFormat.format(now);
        return date;
    }
}

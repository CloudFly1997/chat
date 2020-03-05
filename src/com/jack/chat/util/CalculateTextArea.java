package com.jack.chat.util;

import javafx.scene.control.TextArea;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/4 23:41
 */

public class CalculateTextArea {
    public static TextArea getTextArea(String message) {
        TextArea textArea = new TextArea();
        int fullAngleCount = 0, halfAngleCount = 0;
        char[] messageArray = message.toCharArray();
        for (int i = 0; i < messageArray.length; i++) {
            String temp = String.valueOf(messageArray[i]);
            // 判断是全角字符
            if (temp.matches("[^\\x00-\\xff]")) {
                fullAngleCount++;
            }
            // 判断是半角字符
            else {
                halfAngleCount++;
            }
        }
        double columnCount = Math.ceil(fullAngleCount + halfAngleCount * 0.5);
        int rowCount = (int) Math.ceil((columnCount / 15) + 1);
        if (columnCount > 15) {
            columnCount = 15;
        }
        textArea.setPrefColumnCount((int) columnCount);
        textArea.setPrefRowCount(rowCount);
        return textArea;
    }
}

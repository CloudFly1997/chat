package com.jack.chat.common;

import com.jack.chat.controller.MainWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/18 19:47
 */

public class MainWindowHolder {
    private static MainWindowHolder mainWindowHolder = null;
    private List<MainWindow> mainWindows = new ArrayList<>();
    private MainWindowHolder() {

    }

    public static MainWindowHolder getInstance(){
        if (mainWindowHolder == null) {
            mainWindowHolder = new MainWindowHolder();
        }
        return mainWindowHolder;
    }

    public void  setMainWindow(MainWindow mainWindow) {
        mainWindows.add(mainWindow);
    }

    public MainWindow getMainWindow() {
        return mainWindows.get(0);
    }
}

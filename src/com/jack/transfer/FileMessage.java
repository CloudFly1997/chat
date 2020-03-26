package com.jack.transfer;

import java.io.File;
import java.io.Serializable;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/26 18:54
 */

public class FileMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private File file;
    private String from;
    private String to;
    private String type;
    private String fileName;

    public FileMessage() {
    }

    public FileMessage(File file, String from, String to,String fileType,String fileName) {
        this.file = file;
        this.from = from;
        this.to = to;
        this.type = fileType;
        this.fileName = fileName;
    }


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}

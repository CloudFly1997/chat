package com.jack.chat.pojo;

import java.io.InputStream;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/18 20:26
 */

public class Group implements CommonIndividual {
    private String groupAccount;
    private String groupName;
    private String groupIntroduce;
    private InputStream inputStream;
    private String groupHolder;

    public Group() {
    }

    public Group(String groupAccount, String groupName, String groupIntroduce, InputStream inputStream,
                 String groupHolder) {
        this.groupAccount = groupAccount;
        this.groupName = groupName;
        this.groupIntroduce = groupIntroduce;
        this.inputStream = inputStream;
        this.groupHolder = groupHolder;
    }

    public String getGroupAccount() {
        return groupAccount;
    }

    public void setGroupAccount(String groupAccount) {
        this.groupAccount = groupAccount;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupIntroduce() {
        return groupIntroduce;
    }

    public void setGroupIntroduce(String groupIntroduce) {
        this.groupIntroduce = groupIntroduce;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getGroupHolder() {
        return groupHolder;
    }

    public void setGroupHolder(String groupHolder) {
        this.groupHolder = groupHolder;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupAccount='" + groupAccount + '\'' +
                ", groupName='" + groupName + '\'' +
                ", groupIntroduce='" + groupIntroduce + '\'' +
                ", inputStream=" + inputStream +
                ", groupHolder='" + groupHolder + '\'' +
                '}';
    }

    @Override
    public InputStream getAvatarInputStream() {
        return getInputStream();
    }

    @Override
    public String getId() {
        return getGroupAccount();
    }
}

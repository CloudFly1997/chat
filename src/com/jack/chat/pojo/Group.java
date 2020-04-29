package com.jack.chat.pojo;

import java.io.BufferedInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/18 20:26
 */

public class Group implements CommonIndividual {
    private String groupAccount;
    private String groupName;
    private String groupIntroduce;
    private BufferedInputStream inputStream;
    private String groupHolder;
    private Map<String, User> members = new HashMap<>();

    public Group() {
    }

    public Group(String groupAccount, String groupName, String groupIntroduce, BufferedInputStream inputStream, String groupHolder) {
        this.groupAccount = groupAccount;
        this.groupName = groupName;
        this.groupIntroduce = groupIntroduce;
        this.inputStream = inputStream;
        this.groupHolder = groupHolder;
    }

    public Map<String, User> getMembers() {
        return members;
    }

    public void setMembers(Map<String, User> members) {
        this.members = members;
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

    public BufferedInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(BufferedInputStream inputStream) {
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
    public BufferedInputStream getAvatarInputStream() {
        return getInputStream();
    }

    @Override
    public String getId() {
        return getGroupAccount();
    }
}

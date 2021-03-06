package com.jack.chat.pojo;

import java.io.BufferedInputStream;

/**
 * @author jack
 */
public class User implements CommonIndividual {
    private String account;
    private String password;
    private String nickName;
    private String gender;
    private String birthday;
    private String address;
    private String phoneNumber;
    private String email;
    private String signature;
    private String friend_remark;
    private BufferedInputStream avatar;


    public User(String account, String nickName, String gender, String birthday, String address, String phoneNumber,
                   String email, String signature,BufferedInputStream avatar) {
        this.account = account;
        this.nickName = nickName;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.signature = signature;
        this.avatar = avatar;
    }

    public User() {

    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getSignature() {
        return signature;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getFriend_remark() {
        return friend_remark;
    }

    public void setFriend_remark(String friend_remark) {
        this.friend_remark = friend_remark;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public BufferedInputStream getAvatar()  {

        return avatar;
    }

    public void setAvatar(BufferedInputStream avatar)  {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", signature='" + signature + '\'' +
                ", friend_remark='" + friend_remark + '\'' +
                ", avatar=" + avatar +
                '}';
    }

    @Override
    public BufferedInputStream getAvatarInputStream() {
        return getAvatar();
    }

    @Override
    public String getId() {
        return getAccount();
    }
}

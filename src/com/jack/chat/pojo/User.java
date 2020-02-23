package com.jack.chat.pojo;

public class User {
    private String account;
    private String password;
    private String nickName;
    private String gender;
    private String birthday;
    private String address;
    private Integer phoneNumber;
    private String email;
    private String signature;

    public User(String account,  String nickName, String gender, String birthday, String address, Integer phoneNumber, String email, String signature) {
        this.account = account;
        this.password = password;
        this.nickName = nickName;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.signature = signature;
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

    public Integer getPhoneNumber() {
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

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

}

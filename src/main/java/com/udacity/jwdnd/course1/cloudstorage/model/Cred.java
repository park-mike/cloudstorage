package com.udacity.jwdnd.course1.cloudstorage.model;

public class Cred {
    private Integer credID;
    private Integer userID;
    private String url;

    private String key;
    private String userName;
    private String password;

    public Cred(Integer credID, Integer userID, String url, String key, String userName, String password) {
        this.credID = credID;
        this.userID = userID;
        this.url = url;
        this.userName = userName;
        this.key = key;
        this.password = password;

    }
    public Cred() {}

    public Cred(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public Integer getCredID() {
        return credID;
    }

    public void setCredID(Integer credID) {
        this.credID = credID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

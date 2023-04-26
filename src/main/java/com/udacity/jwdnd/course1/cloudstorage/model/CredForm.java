package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredForm {
    private String userName;
    private String password;
    private String url;
    private Integer credID;


    public CredForm(){}

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCredID() {
        return credID;
    }

    public void setCredID(Integer credID) {
        this.credID = credID;
    }



}

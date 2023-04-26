package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Cred;

public class CredService {
    private final UserMapper userMapper;
    private final CredMapper credmapper;

    public CredService(UserMapper userMapper, CredMapper credmapper) {
        this.userMapper = userMapper;
        this.credmapper = credmapper;
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public CredMapper getCredMapper() {
        return credmapper;
    }

    public Integer addCred(Cred cred) {return credmapper.insert(cred);}
    /**
    public addCred(String credUserName, String userName, String url, String key, String password) {
        Integer userID = userMapper.getUser(userName).getUserID();
        Cred cred = new Cred(0, userID, credUserName, url, key, password);
        credmapper.insert(cred);
    } **/

    public Cred[] getCredListings(Integer userID) {
        return credmapper.getCredListings(userID);
    }

    // added static to work with credcontroller getCred
    public Cred getCred(Integer credID) {
        return credmapper.getCred(credID);
    }

    public void deleteCred(Integer noteID) {
        credmapper.deleteCred(noteID);
    }

    public void updateCred(Integer credID, String newUserName, String url, String key, String password) {
        credmapper.updateCred(credID, newUserName, url, key, password);
    }
}

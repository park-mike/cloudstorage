package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("credential")
public class CredController {
    @Autowired
    private CredService credService;
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private final UserService userService;
    public CredController(CredService credService, EncryptionService encryptionService, UserService userService) {
        this.credService = credService;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }
    @GetMapping
    public String getHomePage(
            Authentication authentication, @ModelAttribute("newFile") FileForm newFile, @ModelAttribute("newCred") CredForm newCred,
            @ModelAttribute("newNote") NoteForm newNote, Model model) {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        model.addAttribute("cred", this.credService.getCredListings(user.getUserID()));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }
// to edit
    @PostMapping("/add-credential")
    public String addCred(
            Authentication authentication, @ModelAttribute("newFile") FileForm newFile, @ModelAttribute("newCred") CredForm newCred,
            @ModelAttribute("newNote") NoteForm newNote, Model model) {

        try{

        User user = userService.getUser(authentication.getName());
        //Integer credID = newCred.getCredID();

        String userName = authentication.getName();
        String newUrl = newCred.getUrl();
        Integer credID = newCred.getCredID();
        String password = newCred.getPassword();

        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);

        if (credID == null) {
            Cred cred = new Cred();
            cred.setUrl(cred.getUrl());
            cred.setUserName(cred.getUserName());
            cred.setUserID(cred.getUserID());
            cred.setKey(encodedKey);
            cred.setPassword(encryptedPassword);
            credService.addCred(cred);
/**
 if (credIDString.isEmpty()) {
 credService.addCred(newCred.getUserName(), userName, newUrl, encodedKey, encryptedPassword);
 } else {
 Cred existingCred = getCred(Integer.parseInt(credIDString));
 credService.updateCred(existingCred.getCredID(), newCred.getUserName(), newUrl, encodedKey, encryptedPassword);
 }  **/

            user = userService.getUser(userName);
            model.addAttribute("credentials", credService.getCredListings(user.getUserID()));
            model.addAttribute("encryptionService", encryptionService);
            model.addAttribute("result", "success");
        } } catch (Exception e) {
            model.addAttribute("result", "error");
        }
        return "result";
    }
    /**
    @GetMapping(value = "/get-credential/{credentialID}")
    public Cred getCred(@PathVariable Integer credID) {
        return credService.getCred(credID);
    } **/

    @GetMapping(value = "/delete-credential/{credID}")
    public String deleteCred(
            Authentication authentication, @PathVariable Integer credID, @ModelAttribute("newCredential") CredForm newCred,
            @ModelAttribute("newFile") FileForm newFile, @ModelAttribute("newNote") NoteForm newNote, Model model) {
        credService.deleteCred(credID);
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        model.addAttribute("cred", this.credService.getCredListings(user.getUserID()));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("result", "success");
        return "result";
    }
}
package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredForm;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final FileService fileService;
    private final UserService userService;
    private final NoteService noteService;
    private final CredService credService;
    private final EncryptionService encryptionService;


    public HomeController(FileService fileService, UserService userService, NoteService noteService, CredService credService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credService = credService;
        this.encryptionService = encryptionService;
    }

    @GetMapping(value = "/get-file/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)

    public String getHomePage(
            Authentication authentication, @ModelAttribute("newFile") FileForm newFile, @ModelAttribute("newNote") NoteForm newNote, @ModelAttribute("nweCred") CredForm newCred, Model model) {
        Integer userID = getUserId(authentication);
        model.addAttribute("files", this.fileService.getFileListings(userID));
        model.addAttribute("notes", noteService.getNoteListings(userID));
        model.addAttribute("cred", credService.getCredListings(userID));

        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    private Integer getUserId(Authentication authentication) {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        return user.getUserID();
    }

    @PostMapping
    public String newFile(Authentication authentication, @ModelAttribute("newFile") FileForm newFile, @ModelAttribute("newNote") NoteForm newNote, @ModelAttribute("newCred") CredForm newCred, Model model) throws
            IOException {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        Integer userID = user.getUserID();
        String[] fileListings = fileService.getFileListings(userID);
        MultipartFile multipartFile = newFile.getFile();
        String fileName = multipartFile.getOriginalFilename();
        boolean fileDup = false;
        //for ({}) {
        if (fileListings.equals(fileName)) {fileDup = true;
        //    break;
        } else if (!fileDup) {
            fileService.addFile(multipartFile, userName);
            model.addAttribute("result", "success");
        } else {
            model.addAttribute("result", "error");
            model.addAttribute("message", "dup added");
        }
        model.addAttribute("files", fileService.getFileListings(userID));

        return "result";
    }

    /**
    public @ResponseBody
    byte[] getFile(@PathVariable String fileName) {
        return fileService.getFile(fileName).getFileData();
    } **/

    @GetMapping(value = "/delete-file/{fileName}")
    public String deleteFile(
            Authentication authentication, @PathVariable String fileName, @ModelAttribute("newFile") FileForm newFile,
            @ModelAttribute("newNote") NoteForm newNote, @ModelAttribute("newCredential") CredForm newCred, org.springframework.ui.Model model) {
        fileService.deleteFile(fileName);
        Integer userID = getUserId(authentication);
        model.addAttribute("files", fileService.getFileListings(userID));
        model.addAttribute("result", "success");
        return "result";
    }
}


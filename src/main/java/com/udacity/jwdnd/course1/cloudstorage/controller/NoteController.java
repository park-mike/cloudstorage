package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("note")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    /**
     * @PostMapping("/note") public String noteText(
     * @ModelAttribute("userNote") UserNote userNote,
     * Authentication authentication,
     * Model model ) { String username -authentication.getPrincipal().toString(); this}
     * //public NoteController
     **/

    @GetMapping
    public String getHomePage(
            Authentication authentication, @ModelAttribute("newFile") FileForm newFile, @ModelAttribute("newNote") NoteForm newNote,
            @ModelAttribute("newCredential") CredForm newCredential, Model model) {
        Integer userID = getUserID(authentication);
        model.addAttribute("notes", this.noteService.getNoteListings(userID));
        return "home";
    }

    private Integer getUserID(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        return user.getUserID();
    }


    @PostMapping("addNote")
    public String newNote(Authentication authentication, @ModelAttribute("newFile") FileForm newFile, @ModelAttribute("newNote") NoteForm newNote, @ModelAttribute("newCred") CredForm newCred, Model model) {
        String userName = authentication.getName();
        String newTitle = newNote.getTitle();
        String noteIDString = newNote.getNoteID();
        String newBody = newNote.getBody();
        if (noteIDString.isEmpty()) {
            noteService.addNote(newTitle, newBody, userName);
        } else {
            Note existingNote = getNote(Integer.parseInt(noteIDString));
            noteService.updateNote(existingNote.getNoteID(), newTitle, newBody);
        }
        Integer userID = getUserID(authentication);
        model.addAttribute("notes", noteService.getNoteListings(userID));
        model.addAttribute("result", "success");
        return "result ";
    }

    @GetMapping(value = "/getNote/{noteID}")
    public Note getNote(@PathVariable Integer noteID) {
        return noteService.getNote(noteID);
    }

    //may need to edit
    @GetMapping(value = "/deleteNote/{noteID}")
    public String deleteNote(
            Authentication authentication, @PathVariable Integer noteID, @ModelAttribute("newNote") NoteForm noteForm,
            @ModelAttribute("newFile") FileForm newFile, @ModelAttribute("newCred") CredForm credForm,
            Model model) {
        noteService.deleteNote(noteID);
        Integer userID = getUserID(authentication);
        model.addAttribute("notes", noteService.getNoteListings(userID));
        model.addAttribute("result", "success");
        return "result";
    }
}


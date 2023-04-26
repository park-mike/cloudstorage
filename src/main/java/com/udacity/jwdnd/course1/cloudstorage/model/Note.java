package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
    private Integer noteID;
    private Integer userID;
    private String noteTitle;
    private String noteBody;

    public Note(Integer noteID, Integer userID, String noteTitle, String noteBody) {
        this.noteID = noteID;
        this.userID = userID;
        this.noteTitle = noteTitle;
        this.noteBody = noteBody;
    }

    public Note(String noteTitle, String noteBody) {
        this.noteTitle = noteTitle;
        this.noteBody = noteBody;
    }

    public Integer getNoteID() {
        return noteID;
    }

    public void setNoteID(Integer noteID) {
        this.noteID = noteID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

}

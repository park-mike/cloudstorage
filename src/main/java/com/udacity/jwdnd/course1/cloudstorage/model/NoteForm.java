package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteForm {
    private String title;
    private String body;
    private String noteID;

    public String getNoteID() {
        return noteID;
    }

    public void setNoteID() {
        this.noteID = noteID;
    }

    public String getBody() {
        return body;
    }

    public void setBody() {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle() {
        this.title = title;
    }
}

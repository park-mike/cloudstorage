package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    private final UserMapper userMapper;
    private final NoteMapper noteMapper;

    public NoteService(UserMapper userMapper, NoteMapper noteMapper) {
        this.userMapper = userMapper;
        this.noteMapper = noteMapper;
    }

    public void addNote(String title, String body, String userName) {
        Integer userID = userMapper.getUser(userName).getUserID();
        Note note = new Note(0, userID, title, body);
        noteMapper.insert(note);
    }

    public Note[] getNoteListings(Integer userID) {
        return noteMapper.getNotesForUser(userID);
    }

    public Note getNote(Integer noteID) {
        return noteMapper.getNote(noteID);
    }

    public void deleteNote(Integer noteID) {
        noteMapper.deleteNote(noteID);
    }

    public void updateNote(Integer noteID, String title, String body) {
        noteMapper.updateNote(noteID, title, body);
    }
}

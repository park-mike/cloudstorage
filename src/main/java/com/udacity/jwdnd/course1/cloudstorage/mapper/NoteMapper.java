package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

// create POJO public class SimpleClass { private String name; private String desc; then setters and getters}
// create Mapper @Mapper public interface SimpleMapper { Simple Dest sourceToDest(SimpleSource source); SimpleSource destSource

public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userID = #{userID}")
    Note[] getNotesForUser(Integer userID);

    @Select("SELECT * FROM NOTES")
    Note[] getNoteListings();

    @Select("SELECT * FROM NOTES WHERE noteID = #{noteID}")
    Note getNote(Integer noteID);

    @Insert("INSERT INTO NOTES(userID, notetitle, notdescription)" +
            "VALUES(#{userID}), #{noteTitle}, #{noteDescription}")

    @Delete("DELETE FROM NOTES WHERE noteID = #{noteID}")
    void deleteNote(Integer noteID);

    @Update("UPDATE NOTES SET notetitle = #{title}, notedescription = #{description} WHERE noteid = #{noteID}")
    void updateNote(Integer noteID, String title, String description);

    @Options(useGeneratedKeys = true, keyProperty = "noteID")
    int insert(Note note);
}



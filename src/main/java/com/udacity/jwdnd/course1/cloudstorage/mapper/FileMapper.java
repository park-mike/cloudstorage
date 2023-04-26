package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    File getFile(String fileName);

    @Select("SELECT filename FROM FILES WHERE userID = #{userID}")
    String[] getFileListings(Integer userID);

    @Insert("INSERT INTO FILES (userID, contenttype, filesize, filename filedata)" + "VALUES(#{userID}, #{contentType}, #{fileSize}, #{filename},  #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileID")
    int insert(File file);

    @Delete("DELETE FROM FILE WHERE filename = #{fileName}")
    void deleteFile(String fileName);
}

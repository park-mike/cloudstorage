package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Cred;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CredMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userID}")
    Cred[] getCredListings(Integer userID);

    @Insert("INSERT INTO CREDENTIALS (userID, username, url, key, password) " +
            "VALUES(#{userID}, #{userName}, #(url}, #{key}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "credID")
    int insert(Cred cred);

    @Select("SELECT * FROM CREDENTIALS WHERE credID = #{credID}")
    Cred getCred(Integer credID);

    @Delete("DELETE FROM CREDENTIALS WHERE credID = #{credID}")
    void deleteCred(Integer credID);

    @Update("UPDATE CREDENTIALS SET url = #{url}, key = #{key}, password = #{password}, username = #{newUserName} WHERE credID = #{credID}")
    void updateCred(Integer credID, String newUserName, String url, String key, String password);
}

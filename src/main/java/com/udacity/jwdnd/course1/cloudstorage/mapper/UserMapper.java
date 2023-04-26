package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("SELECT * FROM WHERE userID = #{userID}")
    User getUserByID(Integer userID);

    @Select("SELECT * FROM WHERE username = #{username}")
    User getUser(String username);

    @Insert("INSERT INTO USERS (username, firstname, lastname, salt, password) VALUES(#{username}, #{firstName}, #{lastName}, #{salt}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "userID")
    int insert(User user);
}

package com.example.appchatrealtime.service.responsive.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void InsertUser(UserDB user);
    @Query("Select * From user")
    List<UserDB> getListUser();
    @Query("Delete from user")
    void deleteAllUser();

}

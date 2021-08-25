package com.example.appchatrealtime.service.responsive.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ListMessageDAO {
    @Insert
    void InsertUser(ListMessageDB listMessageDB);
    @Query("Select * From ListMessage")
    List<ListMessageDB> getListUser();
    @Query("Delete from ListMessage")
    void deleteAllUser();
}

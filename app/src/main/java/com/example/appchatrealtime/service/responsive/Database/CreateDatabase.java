package com.example.appchatrealtime.service.responsive.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserDB.class,ListMessageDB.class},version = 1)
public abstract class CreateDatabase extends RoomDatabase {
    private static final String Database_name="appchat.db";
    private static CreateDatabase instance;


    public static synchronized CreateDatabase getInstance(Context context){
        if (instance ==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),CreateDatabase.class,Database_name)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract UserDAO userDAO();
    public abstract ListMessageDAO listMessageDAO();

}

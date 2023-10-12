package com.attitud.ssc.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteData.class} , version = 2)
public abstract class MyDatabase extends RoomDatabase {
    public abstract MyDao myDao();

    public static MyDatabase database;

    public static synchronized MyDatabase getInstance(Context context){
        if (database == null){
            database = Room.databaseBuilder(context,
                    MyDatabase.class, "room_db").build();
        }

        return database;
    }


}

package com.example.classmanagementapp.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.classmanagementapp.Models.CClass;

@Database(entities = CClass.class, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB database;
    private static String DATABASE_NAME = "ClassManagement";

    public synchronized static RoomDB getInstance(Context context) {
        if(database == null) {
            database = Room.databaseBuilder(
                    context.getApplicationContext(),
                    RoomDB.class,
                    DATABASE_NAME
            )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }


    public abstract MainDAO mainDAO();
}

package com.example.classmanagementapp.Database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.classmanagementapp.Models.CClass;

import java.util.List;

@Dao
public interface MainDAO {
    @Insert(onConflict = REPLACE)
    void insert(CClass cClass);

    @Query("SELECT * from classes ORDER BY ID ASC")
    List<CClass> getAll();

    @Query("UPDATE classes SET subjectName = :subjectName, teacherName = :teacherName, roomName = :roomName, weekOfDay = :weekOfDay, onlineLink = :onlineLink, remarkText = :remarkText, startTime = :startTime, endTime = :endTime WHERE ID = :id")
    void update(int id, String subjectName, String teacherName, String roomName,
                String weekOfDay, String onlineLink, String remarkText, String startTime, String endTime);

    @Query("UPDATE classes SET isActive = :newState WHERE ID = :id")
    void updateIsActiveState(int id, boolean newState);

    @Delete
    void delete(CClass cClass);
}

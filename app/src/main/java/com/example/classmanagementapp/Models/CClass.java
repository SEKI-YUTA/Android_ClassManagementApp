package com.example.classmanagementapp.Models;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

public class CClass implements Serializable {
//    private String subjectName, teacherName, roomName,
//            weekOfDay, onlineLink, remarkText;
    @PrimaryKey(autoGenerate = true)
    int ID = 0;

    @ColumnInfo(name = "subjectName")
    String subjectName;

    @ColumnInfo(name = "teacherName")
    String teacherName;

    @ColumnInfo(name = "roomName")
    String roomName;

    @ColumnInfo(name = "weekOfDay")
    String weekOfDay;

    @ColumnInfo(name = "onlineLink")
    String onlineLink;

    @ColumnInfo(name = "remarkText")
    String remarkText;

    @ColumnInfo(name = "startTime")
    Date startTime;

    @ColumnInfo(name = "endTime")
    Date endTime;

    public CClass(String subjectName, String teacherName, String roomName, String weekOfDay,
                  String onlineLink, String remarkText, Date startTime, Date endTime) {
        this.subjectName = subjectName;
        this.teacherName = teacherName;
        this.roomName = roomName;
        this.weekOfDay = weekOfDay;
        this.onlineLink = onlineLink;
        this.remarkText = remarkText;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getWeekOfDay() {
        return weekOfDay;
    }

    public void setWeekOfDay(String weekOfDay) {
        this.weekOfDay = weekOfDay;
    }

    public String getOnlineLink() {
        return onlineLink;
    }

    public void setOnlineLink(String onlineLink) {
        this.onlineLink = onlineLink;
    }

    public String getRemarkText() {
        return remarkText;
    }

    public void setRemarkText(String remarkText) {
        this.remarkText = remarkText;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}

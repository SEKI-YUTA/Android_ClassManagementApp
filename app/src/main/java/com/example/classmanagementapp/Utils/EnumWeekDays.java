package com.example.classmanagementapp.Utils;

public enum EnumWeekDays {
    Monday("月曜日"),
    Tuesday("火曜日"),
    Wednesday("水曜日"),
    Thursday("木曜日"),
    Friday("金曜日"),
    Saturday("土曜日"),
    Sunday("日曜日");
    private String day;
    private EnumWeekDays(String day) {
        this.day = day;
    }

    public String getWeekDay() {
        return this.day;
    }
}

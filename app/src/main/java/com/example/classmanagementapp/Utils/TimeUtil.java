package com.example.classmanagementapp.Utils;

import android.content.Context;

import com.example.classmanagementapp.R;

import java.util.Arrays;
import java.util.Date;

public class TimeUtil {
    private static long prevRun;
    // String hm 11:11のような形式の文字列をDate型に変換
    public static Date convertDateFromHM(String hm) {
        Date emit = new Date();
        String hour = hm.split(":")[0];
        String min = hm.split(":")[1];
        emit.setHours(Integer.parseInt(hour));
        emit.setMinutes(Integer.parseInt(min));
        return emit;
    }

    // String weekDay 月曜日　火曜日　などの形式の曜日
    // Context context getResources()を使うため
    public static int getWeekDayIndexJa(String weekDay, Context context) {
        String[] weekDaysJa = context.getResources().getStringArray(R.array.weekdays);
        int index = Arrays.asList(weekDaysJa).indexOf(weekDay);
        return index;
    }

    // String weekDay Mon　Tue　などの形式の曜日
    // Context context getResources()を使うため
    public static int getWeekDayIndexEn(String weekDayEn, Context context) {
        String[] weekDaysJa = context.getResources().getStringArray(R.array.weekdaysEn);
        int index = Arrays.asList(weekDaysJa).indexOf(weekDayEn);
        return index;
    }

    public static boolean runOnDoubleTap() {
        Date date = new Date();
        if(prevRun == 0) {
            prevRun = date.getTime();
            return false;
        } else if(date.getTime() - prevRun < 2000) {
            return true;
        } else {
            prevRun = date.getTime();
            return false;
        }
    }


}

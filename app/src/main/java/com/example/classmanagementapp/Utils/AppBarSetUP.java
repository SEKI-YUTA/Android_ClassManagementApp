package com.example.classmanagementapp.Utils;

import androidx.appcompat.app.ActionBar;

public class AppBarSetUP {
    public static void hideTitle(ActionBar actionBar) {
        actionBar.setDisplayShowTitleEnabled(false);
    }
    public static void applyCustomLayout(ActionBar actionBar,int layout) {
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(layout);
    }
}

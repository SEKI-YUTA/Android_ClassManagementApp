package com.example.classmanagementapp.Utils;

import androidx.appcompat.app.ActionBar;

public class AppBarSetUP {
    // ActionBar actionBar タイトルを隠すアクションバー
    public static void hideTitle(ActionBar actionBar) {
        actionBar.setDisplayShowTitleEnabled(false);
    }

    // ActionBar actionBar カスタムレイアウトを許可するアクションバー
    // int layout 適用するカスタムレイアウト
    public static void applyCustomLayout(ActionBar actionBar,int layout) {
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(layout);
    }
}

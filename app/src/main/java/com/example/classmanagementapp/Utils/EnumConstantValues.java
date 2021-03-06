package com.example.classmanagementapp.Utils;

public enum EnumConstantValues {
    NUM_PAGES("",7),
    WEEKDAY_KEY("曜日", -1),
    DATE_KEY("日付", -1),
    IS_BACKED("isBacked", -1),
    VIEWPAGER_OFFSET("viewPagerOffset", -1),
    ONE_CCLASS_KEY("singleCClass", -1),
    ALL_CCLASS_KEY("allCClass", -1),
    URL_VALIDATION("\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", -1);
    private String constantString;
    private int constantInt;
    private EnumConstantValues(String constantString, int constantInt) {
        this.constantInt = constantInt;
        this.constantString = constantString;
    }

    public String getConstantString() {
        return this.constantString;
    }

    public int getConstantInt() {
        return this.constantInt;
    }
}

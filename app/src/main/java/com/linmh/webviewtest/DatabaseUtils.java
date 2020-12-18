package com.linmh.webviewtest;

import android.content.Context;
import android.content.SharedPreferences;

public class DatabaseUtils {
    private static Context mContext;
    private static SharedPreferences sharedPreferences;

    public static void init(Context context) {
        mContext = context;
       sharedPreferences = mContext.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    }

    public static void save(String key, String value) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static String getString(String key) {
        String string = sharedPreferences.getString(key, "");
        return string;
    }


}

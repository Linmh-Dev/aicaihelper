package com.linmh.webviewtest;

import android.app.Application;
import android.util.Log;

public class InitApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseUtils.init(this);
        String userId = DatabaseUtils.getString("userId");
        String cookie = DatabaseUtils.getString("cookie");
        String playMode = DatabaseUtils.getString("playMode");
        String money = DatabaseUtils.getString("money");
        String name = DatabaseUtils.getString("userName");


       // Log.e("lin", userId + "--\n" + cookie + "\n" + playMode + "\n" + money + "");

        UserInfo.setMoney(money);
        UserInfo.setUserName(name);
        UserInfo.setMode(playMode);
        UserInfo.setId(userId);
        UserInfo.setCookie(cookie);

    }
}

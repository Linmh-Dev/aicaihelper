package com.linmh.webviewtest;

public class UserInfo {
    public static String id = "";
    //JSESSIONID=C6D67BB09D6DE98009DD091D6BA154CA
    public static String cookie = "";

    //限定余额
    public static String money;

    //模式
    public static String playMode;

    //用户名
    public static String userName;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        UserInfo.userName = userName;
    }

    public static int getMoney() {
        if (money.equals("") || money == null) {
            money = "0";
        }
        return Integer.parseInt(money);
    }

    public static void setMoney(String money) {
        UserInfo.money = money;
    }

    public static String getMode() {
        return playMode;
    }

    public static void setMode(String mode) {
        UserInfo.playMode = mode;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        UserInfo.id = id;
    }

    public static String getCookie() {
        return cookie;
    }

    public static void setCookie(String cookie) {
        UserInfo.cookie = cookie;
    }
}

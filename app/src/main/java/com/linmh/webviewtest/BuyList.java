package com.linmh.webviewtest;

public class BuyList {
    private static final int[] array = new int[]{5,15,40,120,300,650,1500,4000,10000,25000};
    private  static int flag=0;

    public static int getNum() {
        return array[flag];
    }

    public static String getNumStr() {
        return getNum()+"";
    }

    public static void restart() {
        flag = 0;
    }

    public static void add() {
        flag = flag +1;
    }
}

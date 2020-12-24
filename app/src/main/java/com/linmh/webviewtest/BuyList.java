package com.linmh.webviewtest;

public class BuyList {

    private static final int[] array1 = new int[]{5, 15, 40, 120, 300, 650, 1500, 4000, 10000, 25000};
    private static final int[] array2 = new int[]{10, 30, 80, 240, 600, 1300, 3000, 8000, 20000, 50000};
    private static  int[] array = array1;
    private static volatile int flag = 0;

    public static int getNum() {
        return array[flag];
    }

    public static String getNumStr() {
        return getNum() + "";
    }

    public static void restart() {
        flag = 0;
    }

    public static synchronized void add() {
        flag = flag + 1;
    }

    public static void swapMulti() {
        array = array2;
        flag = 0;
    }
    public static void swapSingle() {
        array = array1;
        flag = 0;
    }

}

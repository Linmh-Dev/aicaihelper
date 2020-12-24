package com.linmh.webviewtest;

import java.util.Random;

public class Award {
   private static String  awardNum = "1,2,3";

    public static String getAwardNum() {
       return awardNum;
    }

    public static void setAwardNum(String s) {
        awardNum = s;
    }

    private static String random() {
        Random random = new Random();
        int rand1 = random.nextInt(6) + 1;
        int rand2 = random.nextInt(6) + 1;
        int rand3 = random.nextInt(6) + 1;

        return rand1 + "," + rand2 + "," + rand3;
    }

}

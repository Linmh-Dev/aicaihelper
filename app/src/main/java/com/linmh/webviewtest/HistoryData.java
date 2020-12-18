package com.linmh.webviewtest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class HistoryData {
    private static Map<String, Integer> map = new HashMap<>();
    private static  int a = -1;
    private static  int zzz = 0;
    public static int getZzz() {
        return zzz;
    }

    public static void setZzz(int zzz) {
        HistoryData.zzz = zzz;
    }




    /**
     * 获取前一期的大小双单
     * @param curr
     * @return
     */
    public static int getPrePried() {

       /* if (map.containsKey(curr)) {
            return map.get(curr);
        }
        return -100;*/
        return a;
    }

    /**
     *
     * @param curr 期数
     * @param num 大小双单
     * 0表示小,1表示大，3 表示单， 2 表示双
     */
    public static void setCurrent(int num) {
       /* if (!map.containsKey(curr)) {
            map.put(curr, num);
        }*/
        a = num;
    }

    public static boolean contains(String curr) {
        return map.containsKey(curr);
    }

}

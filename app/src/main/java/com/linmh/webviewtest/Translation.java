package com.linmh.webviewtest;

public class Translation {
    //前对,1表示大，0表示小
    public static int qiandui(int a, int b, int c) {
        int sum = a + b + c;
        if (a == 1 && b == 1 && sum <= 10) {
            return 1;
        } else if (a == 1 && b == 1 && sum > 10) {
            return 0;
        } else if (a == 2 && b == 2 && sum <= 10) {
            return 0;
        } else if (a == 2 && b == 2 && sum > 10) {
            return 1;
        } else if (a == 3 && b == 3 && sum <= 10) {
            return 0;
        } else if (a == 3 && b == 3 && sum > 10) {
            return 1;
        } else if (a == 4 && b == 4 && sum <= 10) {
            return 0;
        } else if (a == 4 && b == 4 && sum > 10) {
            return 1;
        } else if (a == 6 && b == 6 && sum <= 10) {
            return 1;
        } else if (a == 6 && b == 6 && sum > 10) {
            return 0;
        }
        return -1;
    }






    // 3 表示单， 2 表示双
    public static int houdui(int a, int b, int c) {
        int sum = (a + b + c) % 2;
        if (a == b) {
            return -1;
        } else if (b == 1 && c == 1 && sum == 1) {
            return 2;
        } else if (b == 1 && c == 1 && sum == 0) {
            return 3;
        } else if (b == 3 && c == 3 && sum == 1) {
            return 2;
        } else if (b == 3 && c == 3 && sum == 0) {
            return 3;
        } else if (b == 5 && c == 5 && sum == 1) {
            return 2;
        } else if (b == 5 && c == 5 && sum == 0) {
            return 3;
        } else if (b == 2 && c == 2 && sum == 1) {
            return 3;
        } else if (b == 2 && c == 2 && sum == 0) {
            return 2;
        } else if (b == 4 && c == 4 && sum == 1) {
            return 3;
        } else if (b == 4 && c == 4 && sum == 0) {
            return 2;
        } else if (b == 6 && c == 6 && sum == 1) {
            return 3;
        } else if (b == 6 && c == 6 && sum == 0) {
            return 2;
        }

        return -1;
    }

    public static int hunda(int a, int b, int c) {
        int re = -1;
        re = qiandui(a, b, c);
        if (re != -1) {
            return re;
        } else {
            re = houdui(a, b, c);
        }

        return re;
    }

    //100 中奖，200 不买，不中返回上一期
    public static int isSuccess(String curr, int a, int b, int c) {

      /*  String ans = analyze(a, b, c);

        HistoryData.setCurrent(curr, ans);

        Integer prePried = HistoryData.getPrePried(curr);
        if (prePried == -100) {
            return -100;
        } else if (ans == -1) {
            return 200;
        } else if (prePried == ans) {
            return 100;
        } else {
            return prePried;
        }*/

        return 0;

    }

    private static String analyze(int a, int b, int c) {
        int s = a + b + c;
        String rest = "-1";
        switch (s) {
            case 3:
                rest = "小单";
                break;
            case 4:
                rest = "小双";
                break;
            case 5:
                rest = "小单";
                break;
            case 6:
                rest = "小双";
                break;
            case 7:
                rest = "小单";
                break;
            case 8:
                rest = "小双";
                break;
            case 9:
                rest = "小单";
                break;
            case 10:
                rest = "小双";
                break;
            case 11:
                rest = "大单";
                break;
            case 12:
                rest = "大双";
                break;
            case 13:
                rest = "大单";
                break;
            case 14:
                rest = "大双";
                break;
            case 15:
                rest = "大单";
                break;
            case 16:
                rest = "大双";
                break;
            case 17:
                rest = "大单";
                break;
            case 18:
                rest = "大双";
                break;
        }
        return rest;
    }

    public static int nextPried(String curr, int a, int b, int c) {
        int code = isSuccess(curr, a, b, c);
        if (code == 100) {
            //中奖
            int qian = hunda(a, b, c);
            //倍数返回第一个
            BuyList.restart();
            return qian;
        } else if (code == 200) {
            //不买
            return hunda(a, b, c);
        } else if (code == -100) {
            return -100;
        } else {
            //不中,下一个倍数
            BuyList.add();
            return code;
        }
    }

    public static String translator(int n) {

        switch (n) {
            case -100:
                return "还没有上一期，无法计算。";

            case -1:
                return "不买";

            case 1:
                return "大";

            case 0:
                return "小";

            case 2:
                return "双";

            case 3:
                return "单";

        }
        return null;
    }






}

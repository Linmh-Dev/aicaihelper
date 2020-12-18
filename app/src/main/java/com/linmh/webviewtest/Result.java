package com.linmh.webviewtest;

public class Result {
    String code;
    String msg;
    Data data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        String lotteryOpen;
        String issueNo;
        long openTime;

        public long getOpenTime() {
            return openTime;
        }

        public void setOpenTime(long openTime) {
            this.openTime = openTime;
        }

        public String getIssueNo() {
            return issueNo;
        }

        public void setIssueNo(String issueNo) {
            this.issueNo = issueNo;
        }

        public String getLotteryOpen() {
            return lotteryOpen;
        }

        public void setLotteryOpen(String lotteryOpen) {
            this.lotteryOpen = lotteryOpen;
        }
    }
}

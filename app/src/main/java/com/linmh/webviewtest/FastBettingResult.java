package com.linmh.webviewtest;

public class FastBettingResult {
    String code;
    Data data;
    String msg;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    static class Data{

        String accountMoney;
        Result result;

        @Override
        public String toString() {
            return "Data{" +
                    "accountMoney='" + accountMoney + '\'' +
                    ", result=" + result +
                    '}';
        }

        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }

        public String getAccountMoney() {
            return accountMoney;
        }

        public void setAccountMoney(String accountMoney) {
            this.accountMoney = accountMoney;
        }


    }

    static class Result{
        String awardResult;
        String periodNo;

        @Override
        public String toString() {
            return "Result{" +
                    "awardResult='" + awardResult + '\'' +
                    ", periodNo='" + periodNo + '\'' +
                    '}';
        }

        public String getAwardResult() {
            return awardResult;
        }

        public void setAwardResult(String awardResult) {
            this.awardResult = awardResult;
        }

        public String getPeriodNo() {
            return periodNo;
        }

        public void setPeriodNo(String periodNo) {
            this.periodNo = periodNo;
        }
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "FastBettingResult{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
/*
{"code":"success","data":{"result":{"gameId":"K3MMC","awardResult":"2,4,6","betAward":"1.94","winStatus":5,"periodNo":"1724257221","awardDate":1608377117299},"shareOrder":false,"resultForBet":[{"gameId":"K3MMC","gameName":"极速快3","id":2838088168,"playName":"和值","betAmount":1,"betAward":1.94,"status":1,"betContent":"大","createDate":1608377117276,"awardResult":"2,4,6","winStatus":5,"periodEndDate":1608377117276,"isCancel":true,"periodNo":"1724257221","rebate":null,"betType":"digital","billNo":"BT3002867321899","todayBetAmount":null,"todayBetAward":null,"profit":null,"statusStr":"已派彩","cancelBill":false}],"accountMoney":47.94},"msg":"·投注成功·"}

*/
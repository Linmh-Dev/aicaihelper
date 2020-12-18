package com.linmh.webviewtest;

public class BettingResult {
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
        String shareOrder;
        String accountMoney;


        public String getShareOrder() {
            return shareOrder;
        }

        public void setShareOrder(String shareOrder) {
            this.shareOrder = shareOrder;
        }

        public String getAccountMoney() {
            return accountMoney;
        }

        public void setAccountMoney(String accountMoney) {
            this.accountMoney = accountMoney;
        }


    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
/*

{"code":"success","data":{"shareOrder":false,"resultForBet":[{"gameId":"OG1K3","gameName":"1分快3","id":2129008591,"playName":"和值","betAmount":650,"betAward":0.0,"status":1,"betContent":"小","createDate":1607946551740,"awardResult":"未开奖","winStatus":0,"periodEndDate":1607946600000,"isCancel":true,"periodNo":"202012141190","rebate":null,"betType":"
digital","billNo":"BT3002860074792","todayBetAmount":null,"todayBetAward":null,"profit":null,"cancelBill":true,"statusStr":"等待开奖"}],"accountMoney":4730.79},"msg":"·投注成功·"}
 */
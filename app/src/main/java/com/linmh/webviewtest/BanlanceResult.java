package com.linmh.webviewtest;

public class BanlanceResult {

    String code;
    Data data;

    public static class Data{
        public String money;

        @Override
        public String toString() {
            return "Data{" +
                    "money='" + money + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BanlanceResult{" +
                "code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}
/*
{"code":"success","data":{"agentRebates":null,"money":0,"loginName":null,"nickName":null,"loginTime":null,"type":null,"parentId":null,"userStatus":null,"bettingStatus":null,"freezeStatus":null,"blackStatus":null,"cashUserOpen":null,"userLevelOpen":null,"userTags":null,"userDetail":null,"testAccountType":null,"userWithdrawAvail":null,"pwdEncodeType":null,"chatRoom":null,"useManageInvite":null},"msg":null}

 */

package com.linmh.webviewtest;

import android.util.Log;

import java.util.Date;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RequestWrap {




    public static String getBodyString(String priedNum, String money, String codes){
        String json =  "{\"accountId\":" + UserInfo.getId()+"," +
                "\"clientTime\":" + System.currentTimeMillis() +"," +
                "\"gameId\":\"OG1K3\"," +
                "\"issue\":\"" +priedNum +"\"," +
                "\"item\":[\"{\\\"methodid\\\":\\\"K3002001001\\\",\\\"nums\\\":1," +
                "\\\"rebate\\\":\\\"0.00\\\",\\\"times\\\":"+money+",\\\"money\\\":"+money+"," +
                "\\\"playId\\\":[\\\"K3002001007\\\"],\\\"mode\\\":1," +
                "\\\"issueNo\\\":\\\"" + priedNum + "\\\"," +
                "\\\"codes\\\":\\\"" +codes+"\\\"}\"]," +
                "\"encryKey\":\"1607835654437616128061181125\"," +
                "\"encryValue\":\"b06a286d48d490bc5463fbf33d1901e2\"}";

        Log.e("lin", json);

        return json;
    }


    public static Request getRequest(String priedNum, String money, String codes){
        RequestBody requestBody = RequestBody.create(getBodyString(priedNum, money, codes), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                //"http://192.168.123.151:8080/OkHttpServer_war/Betting"
                .url("http://m.524229.com/tools/_ajax/OG1K3/betSingle")//
                .addHeader("cookie", UserInfo.getCookie())
                .addHeader("user-agent", "Mozilla/5.0 (Linux; Android 10; MI 9 SE Build/QKQ1.190828.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/83.0.4103.101 Mobile Safari/537.36 acgapp")
                .addHeader("Origin", "https://m.524229.com:9016")
                .addHeader("Referer", "https://m.524229.com:9016/lottery/K3/OG1K3")
                .post(requestBody)
                .build();

        return request;

    }

    //获取余额
    public static Request getBanlanceRequest(String userName ){
        String body = "{\"userName\":\"" + userName + "\",\"device\":1,\"encryKey\":\"1607944496194521581691192194\",\"encryValue\":\"cd6ceeb06684ba8d9c2f29a6ed3cfc14\"}";
        RequestBody requestBody = RequestBody.create(body, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                //http://192.168.123.151:8080/OkHttpServer_war/GetBalance
                .url("http://88ac18.com/tools/_ajax//getUserBanlance")//"http://88ac18.com/tools/_ajax//getUserBanlance"
                .addHeader("cookie", UserInfo.getCookie())
                .addHeader("user-agent", "Mozilla/5.0 (Linux; Android 10; MI 9 SE Build/QKQ1.190828.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/83.0.4103.101 Mobile Safari/537.36 acgapp")
                .addHeader("Origin", "http://88ac18.com")
                .addHeader("Referer", "http://88ac18.com/home")
                .post(requestBody)
                .build();

        return request;
    }


}

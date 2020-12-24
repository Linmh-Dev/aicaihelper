package com.linmh.webviewtest;

import android.util.Log;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http.RetryAndFollowUpInterceptor;

public class OkHttpSingle {
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .build();

    private static Random random = new Random();

    private static OkHttpClient balanceOK = new OkHttpClient.Builder().retryOnConnectionFailure(false).build();


    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    //codes --> 大，小，双，单
    public static Response request(String priedNum, String money, String codes, RetryConnection retryConnection) {
       // Log.e("lin", priedNum+"---" + money +"---"+ codes);

        Response betting = null;

        try {
            betting = OkHttpSingle.getOkHttpClient().newCall(RequestWrap.getRequest(priedNum, money, codes)).execute();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("lin", "投注失败,重试");
            try {
                retryConnection.requestFailure();
                Thread.sleep(5000);
                if (betting == null) {

                    betting = request(priedNum,money,codes,retryConnection);
                }


            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        retryConnection.requestSuccess();
        return betting;
    }

    public static Response requestFast( String money, String codes, RetryConnection retryConnection) {
        Log.e("lin",  money +"---"+ codes);

        Response betting = null;

        try {
            betting = OkHttpSingle.getOkHttpClient().newCall(RequestWrap.getRequestFast( money, codes)).execute();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("lin", "投注失败,重试");
            try {
                retryConnection.requestFailure();
                Thread.sleep(5000);
                if (betting == null) {

                    betting = requestTest(retryConnection);
                }


            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        retryConnection.requestSuccess();
        return betting;
    }

    public static Response requestTest(RetryConnection retryConnection) {
        String url = "http://88ac18.com/tools/_ajax/getLotteryOpenNewestGame";
        //String url = "http://192.168.123.151:8080/OkHttpServer_war/BetSingle";

        RequestBody requestBody = RequestBody.create("{\"gameId\":\"OG1K3\",\"sourceName\":\"PC\",\"encryKey\":\"1607847817569127954927760540\",\"encryValue\":\"9c5497c41d9282474e9a01a640d4257e\"}",
                MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", "ae0db4a0fa1c246b7dde06476c1a4421=dd0b7e18a7c5879a00d22b13d3f42f07; JSESSIONID=95724070BF4EE91E7B9D30988AF71059")
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36")
                .addHeader("Referer", "http://88ac18.com/home")
                .addHeader("Origin", "http://88ac18.com")
                .post(requestBody)
                .build();
        Response execute = null;
        try {
            execute = getOkHttpClient().newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("lin", "重试");
            try {
                retryConnection.requestFailure();
                Thread.sleep(5000);
                if (execute == null) {

                    execute = requestTest(retryConnection);
                }


            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        retryConnection.requestSuccess();

        return execute;

    }

    public static void getTimeMills(Callback callback) {
        String url = "http://88ac18.com/tools/_ajax/getLotteryOpenNewestGame";

        RequestBody requestBody = RequestBody.create("{\"gameId\":\"OG1K3\",\"sourceName\":\"PC\",\"encryKey\":\"1607847817569127954927760540\",\"encryValue\":\"9c5497c41d9282474e9a01a640d4257e\"}",
                MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", "ae0db4a0fa1c246b7dde06476c1a4421=dd0b7e18a7c5879a00d22b13d3f42f07; JSESSIONID=95724070BF4EE91E7B9D30988AF71059")
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36")
                .addHeader("Referer", "http://88ac18.com/home")
                .addHeader("Origin", "http://88ac18.com")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(callback);

    }

    public static String getBlance(String userName) {
        Request request = RequestWrap.getBanlanceRequest(userName);
        try {
            Response execute = balanceOK.newCall(request).execute();
            BanlanceResult banlanceResult = GsonParse.getInstance().fromJson(execute.body().string(), BanlanceResult.class);
            //Log.e("lin", banlanceResult.toString() );
            return banlanceResult.data.money;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("lin", "获取余额出错-->"+e.getMessage());
        }
        return null;
    }

    public static Request getUserId(String cookie) {
        String url = "http://88ac18.com/tools/_ajax/userCardBoxInfo";
        String body = "{\"accountId\":null,\"loginName\":\""+UserInfo.getUserName()+"\",\"isHeader\":true,\"encryKey\":\"1608390291305648002960001380\",\"encryValue\":\"d8c97aaccbc3d5cffee3bbdeac21281a\"}";
        RequestBody requestBody = RequestBody.create(body,
                MediaType.parse("application/json"));

        Log.e("lin", body);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", cookie)
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36")
                .addHeader("Referer", "http://88ac18.com/lottery/K3/OG1K3")
                .addHeader("Origin", "http://88ac18.com")
                .post(requestBody)
                .build();

        return request;
    }
}

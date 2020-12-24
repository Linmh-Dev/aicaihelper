package com.linmh.webviewtest;

import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Response;


public class TestActivity extends BaseActivity {

    AbsParser mode = new QianDuiMode();

    Disposable disposable;

    TextView textView;
    RetryConnection retryConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getSupportActionBar().hide();
        retryConnection = new RetryConnection(this);

        Timer timer = new Timer();

        TextView textView = findViewById(R.id.m_textview);
        ScrollView scrollView = findViewById(R.id.sc_scroll);
        Observable
                .interval(60, TimeUnit.SECONDS)
                .map(new Function<Long, TextView>() {
                    @Override
                    public TextView apply(Long aLong) throws Throwable {
                        return textView;
                    }
                })
                .map(new Function<TextView, String>() {
                    @Override
                    public String apply(TextView textView) throws Throwable {
                        int blance = 0;
                        //账户余额
                        try {
                            float v = Float.parseFloat(OkHttpSingle.getBlance(UserInfo.getUserName()));
                            blance = (int) v;
                            Log.e("lin", blance + "");
                            if (blance >= UserInfo.getMoney()) {
                                Log.e("lin", "达到限定余额，退出");
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        String ans = "";
                        try {
                            Response response = OkHttpSingle.requestTest(retryConnection);
                            String string = response.body().string();
                            Result result = GsonParse.getInstance().fromJson(string, Result.class);
                            String[] split = result.data.getLotteryOpen().split(",");
                            //String s = result.data.issueNo.substring(6);
                            int nextPried = mode.judge( Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                            String codes = AbsParser.translator(nextPried);

                            String msg = "---";
                            String accountMoney = "---";


                            if (blance != 0) {
                                //投注
                                Response response1 = getResponse(codes, blance, result);



                                BettingResult bettingResult = parseBettingResult(response1);
                                if (bettingResult != null) {
                                    accountMoney = bettingResult.data.accountMoney;
                                    msg = bettingResult.msg;
                                } else {
                                    accountMoney = String.valueOf(blance);
                                }
                            }

                            int e = BuyList.getNum();
                            if (codes.equals("不买")) {
                                e = 0;
                            }

                            ans = "上一期：" + result.data.issueNo
                                    + "\n" + "本期："
                                    + codes + "\n"
                                    + "投注结果：" + msg + "\n"
                                    + "投注金额：" + e + "\n"
                                    + "账户余额：" + accountMoney + "\n\n";

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return ans;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        CharSequence text = textView.getText();

                        if (text.length() > 6250) {
                            CharSequence charSequence = text.subSequence(6000, text.length());
                            textView.setText(charSequence + s);
                        } else {
                            textView.append(s);
                        }
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        }, 500);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void test(String ans, String n) {
        String[] split = ans.split(",");
        int nextPried = mode.judge( Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        String aa = "上一期：" + n + "\n" + "本期：" + AbsParser.translator(nextPried) + "\n" + "金额：" + BuyList.getNum() + "\n\n";
        textView.append(aa);
    }

    private String parsePried(String n) {
        long l = Long.parseLong(n);
        return String.valueOf(l + 1);
    }


    private Response getResponse(String codes, int blance, Result result) throws IOException {
        Response response1 = null;
        if (!codes.equals("不买")) {
            // Response response1 = OkHttpSingle.request(parsePried(result.data.issueNo), BuyList.getNumStr(), codes);

            //如果余额少于计划投注金额，则把全部余额投注
            if (blance < BuyList.getNum()) {
                Log.e("lin", "余额少于计划投注金额，全部投注");
                response1 = OkHttpSingle.request(parsePried(result.data.issueNo), String.valueOf(blance), codes, retryConnection);
            } else {
                response1 = OkHttpSingle.request(parsePried(result.data.issueNo), BuyList.getNumStr(), codes, retryConnection);
            }
            return response1;
        }
        return null;
    }

    private BettingResult parseBettingResult(Response response) {
        if (response == null) {
            return null;
        }
        try {
            String res = response.body().string();
            BettingResult bettingResult = GsonParse.getInstance().fromJson(res, BettingResult.class);
            Log.e("lin", res);
            return bettingResult;
        } catch (Exception e) {
            Log.e("lin", "解析投注结果出错：" + e.toString());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}



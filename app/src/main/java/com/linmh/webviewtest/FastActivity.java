package com.linmh.webviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.Subject;
import okhttp3.Response;

public class FastActivity extends AppCompatActivity {
    AbsParser mode = new QianDuiMode();

    TextView textView;
    RetryConnection retryConnection;
    Disposable disposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast);
        getSupportActionBar().hide();
        retryConnection = new RetryConnection(this);

        Timer timer = new Timer();

        TextView textView = findViewById(R.id.m_textview);
        ScrollView scrollView = findViewById(R.id.sc_scroll);
        Observable
                .interval(2, TimeUnit.SECONDS)
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
                            if (blance >= UserInfo.getMoney()) {
                                Log.e("lin", "达到限定余额，退出");
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        String ans = "";
                        try {
                           /* Response response = OkHttpSingle.requestTest(retryConnection);
                            String string = response.body().string();
                            Result result = GsonParse.getInstance().fromJson(string, Result.class);*/
                            String[] split = Award.getAwardNum().split(",");
                            int nextPried = mode.judge(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                            String codes = AbsParser.translator(nextPried);

                            String msg = "---";
                            String accountMoney = "---";


                            if (blance != 0) {
                                //投注
                                Response response1 = getResponse(codes, blance);

                                FastBettingResult fastBettingResult = parseBettingResult(response1);


                                if (fastBettingResult != null) {
                                    Award.setAwardNum(fastBettingResult.data.result.awardResult);
                                    accountMoney = fastBettingResult.data.accountMoney;
                                    //  Log.e("lin", "re-->" + fastBettingResult.data.result.awardResult);
                                    msg = fastBettingResult.msg;
                                } else {
                                    accountMoney = String.valueOf(blance);
                                }
                            }

                            int e = BuyList.getNum();
                            if (codes.equals("不买")) {
                                e = 1;
                            }

                            ans = "本期：" + codes + "\n"
                                    + "投注结果：" + msg + "\n"
                                    + "投注金额：" + e + "\n"
                                    + "本期开奖结果：" + Award.getAwardNum() + "\n"
                                    + "账户余额：" + accountMoney + "\n\n";

                            Log.e("lin", ans);
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
        int nextPried = mode.judge(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        String aa = "上一期：" + n + "\n" + "本期：" + AbsParser.translator(nextPried) + "\n" + "金额：" + BuyList.getNum() + "\n\n";
        textView.append(aa);
    }

    private String parsePried(String n) {
        long l = Long.parseLong(n);
        return String.valueOf(l + 1);
    }


    private Response getResponse(String codes, int blance) throws IOException {
        Response response1 = null;

        Random random = new Random();
        //随机延迟
        int r = Math.max(random.nextInt(10), 5);
        Log.e("lin", "延迟-->" + r);
        try {
            Thread.sleep(r*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!codes.equals("不买")) {
            // Response response1 = OkHttpSingle.request(parsePried(result.data.issueNo), BuyList.getNumStr(), codes);

            //如果余额少于计划投注金额，则把全部余额投注
            if (blance < BuyList.getNum()) {
                Log.e("lin", "余额少于计划投注金额，全部投注");
                response1 = OkHttpSingle.requestFast(String.valueOf(blance), codes, retryConnection);
            } else {
                response1 = OkHttpSingle.requestFast(BuyList.getNumStr(), codes, retryConnection);
            }
            return response1;
        } else {
            //等于不买
            return getResponseFast(codes);
        }

    }

    //买一块
    private Response getResponseFast(String codes) throws IOException {
        if (codes.equals("不买")) {
            return OkHttpSingle.requestFast("1", "大", retryConnection);

        }
        return null;
    }

    private FastBettingResult parseBettingResult(Response response) {
        if (response == null) {
            return null;
        }
        try {
            String res = response.body().string();
            Log.e("lin", "投注结果：---》"+res);
            FastBettingResult bettingResult = GsonParse.getInstance().fromJson(res, FastBettingResult.class);

            return bettingResult;
        } catch (Exception e) {
            Log.e("lin", "解析投注结果出错：" + e.toString());
            e.printStackTrace();
        }
        return null;
    }

    private static String random() {
        Random random = new Random();
        int rand1 = random.nextInt(6) + 1;
        int rand2 = random.nextInt(6) + 1;
        int rand3 = random.nextInt(6) + 1;


        return rand1 + "," + rand2 + "," + rand3;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }

    }
}
package com.linmh.webviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QueryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        TextView textView = findViewById(R.id.tv_result);

            Observable.just(textView)
                    .map(new Function<TextView, String>() {
                        @Override
                        public String apply(TextView textView) throws Throwable {
                            String blance = "---";
                            try {
                                blance = OkHttpSingle.getBlance(UserInfo.getUserName());
                            } catch (Exception e) {
                                System.out.println(e.toString());
                            }

                            return blance;
                        }

                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull String s) {
                            textView.setText("账户余额：" + s);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            textView.setText(e.toString());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });




    }
}
package com.linmh.webviewtest;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class RetryConnection {
    private AlertDialog alertDialog;
    //private int count = 0;
    private Handler handler;

    public RetryConnection(Context mContext) {
        alertDialog = new AlertDialog.Builder(mContext)
                .setCancelable(false)
                .setView(R.layout.alertdialog_layout)
                .create();

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                //message.setText("请求发送失败，正在重试第" + count + "次");
                if (!alertDialog.isShowing()) {
                    alertDialog.show();
                }
            }
        };

    }

    public void requestFailure() {
        if (alertDialog.isShowing()) {
            alertDialog.cancel();
        }

      //  count++;

        handler.sendMessage(Message.obtain());
    }

    public void requestSuccess() {
        if (alertDialog.isShowing()) {
            alertDialog.cancel();
        }

    }

}

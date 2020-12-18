package com.linmh.webviewtest;

import android.util.Log;

public class QianDuiMode extends AbsParser{
    @Override
    public int mode(int a, int b, int c) {
        String mode = UserInfo.getMode();
        if (mode.equals("混打")) {
            Log.e("lin", "混打");
            return hunda(a, b, c);
        } else if (mode.equals("后对")) {
            Log.e("lin", "后对");
            return houdui(a, b, c);
        } else {
            Log.e("lin", "前对");
            return qiandui(a, b, c);
        }

    }
}

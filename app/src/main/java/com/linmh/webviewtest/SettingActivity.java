package com.linmh.webviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    EditText userId, cookie, money,userName;
    Button save;
    RadioGroup radioGroup;
    RadioButton qiandui, houdui, hunda;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();

        String id = DatabaseUtils.getString("userId");
        String cok = DatabaseUtils.getString("cookie");
        String n = DatabaseUtils.getString("userName");
        UserInfo.setMode(DatabaseUtils.getString("playMode"));


        userId.setText(id);
        cookie.setText(cok);
        userName.setText(n);
        money.setText(String.valueOf(UserInfo.getMoney()));

        initRadioBt();

        save.setOnClickListener((v) -> {

            String m = money.getText().toString();
            String u = this.userId.getText().toString();
            String c = cookie.getText().toString();
            String name = userName.getText().toString();

            DatabaseUtils.save("userId", u);
            DatabaseUtils.save("cookie", c);
            DatabaseUtils.save("money", m);
            DatabaseUtils.save("userName", name);


            UserInfo.setCookie(c);
            UserInfo.setId(u);
            UserInfo.setMoney(m);
            UserInfo.setUserName(name);

            Toast.makeText(SettingActivity.this, "保存成功", Toast.LENGTH_SHORT).show();

            finish();
        });

    }

    private void initView() {
        userId = findViewById(R.id.ed_userId);
        cookie = findViewById(R.id.ed_cookie);
        save = findViewById(R.id.bt_save);
        radioGroup = findViewById(R.id.ra_group);
        qiandui = findViewById(R.id.ra_qiandui);
        hunda = findViewById(R.id.ra_hunda);
        houdui = findViewById(R.id.ra_houdui);
        money = findViewById(R.id.ed_moneyy);
        userName = findViewById(R.id.ed_userName);

        RadioBtListener();

    }

    private void RadioBtListener() {
        houdui.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DatabaseUtils.save("playMode", "后对");
                    UserInfo.setMode("后对");
                }

            }
        });
        qiandui.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DatabaseUtils.save("playMode", "前对");
                    UserInfo.setMode("前对");
                    Log.e("lin", "qindui");
                }
            }
        });

        hunda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DatabaseUtils.save("playMode", "混打");
                    UserInfo.setMode("混打");
                }
            }
        });
    }


    private void initRadioBt() {
        String mode = UserInfo.getMode();
        if (mode.equals("前对")) {
            qiandui.setChecked(true);

        } else if (mode.equals("后对")) {
            houdui.setChecked(true);

        } else {
            hunda.setChecked(true);

        }
    }

}
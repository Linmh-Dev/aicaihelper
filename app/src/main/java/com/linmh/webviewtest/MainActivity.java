package com.linmh.webviewtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.linmh.webviewtest.login.LoginActivity;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends BaseActivity {


    Button setting, start, query, fast, login;
    volatile boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fast = findViewById(R.id.bt_fast);
        setting = findViewById(R.id.bt_setting);
        start = findViewById(R.id.bt_start);
        query = findViewById(R.id.bt_query);
        login = findViewById(R.id.bt_login);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        fast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent intent = new Intent(MainActivity.this, FastActivity.class);
                startActivity(intent); */
                if (!flag) {
                    BuyList.swapMulti();
                    Date date = new Date();
                    int seconds = 60 - date.getSeconds() + 10;
                    Log.e("lin", seconds + "----");
                    Timer timer = new Timer();

                    Toast.makeText(MainActivity.this, seconds + "秒后启动", Toast.LENGTH_SHORT).show();

                    flag = true;
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, TestActivity.class);
                            startActivity(intent);
                        }
                    }, seconds * 1000);

                }
            }
        });


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    BuyList.swapSingle();
                    Date date = new Date();
                    int seconds = 60 - date.getSeconds() + 10;
                    Log.e("lin", seconds + "----");
                    Timer timer = new Timer();

                    Toast.makeText(MainActivity.this, seconds + "秒后启动", Toast.LENGTH_SHORT).show();

                    flag = true;
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, TestActivity.class);
                            startActivity(intent);
                        }
                    }, seconds * 1000);

                }
            }


        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);

            }
        });

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QueryActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.setting) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.night_mode) {
            UserInfo.isNight = !UserInfo.isNight;
            DatabaseUtils.save("night", UserInfo.isNight);

            modeSwap(UserInfo.isNight);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
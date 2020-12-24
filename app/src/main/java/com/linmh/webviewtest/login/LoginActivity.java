package com.linmh.webviewtest.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebViewClient;
import com.linmh.webviewtest.BaseActivity;
import com.linmh.webviewtest.DatabaseUtils;
import com.linmh.webviewtest.OkHttpSingle;
import com.linmh.webviewtest.R;
import com.linmh.webviewtest.UserInfo;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {
    AgentWeb go;
    String url1 = UserInfo.url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {

                Boolean obj = (Boolean) msg.obj;
                if (obj) {
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this,"登陆失败 ",Toast.LENGTH_SHORT).show();
                }

            }
        };

        FloatingActionButton floatingActionButton = findViewById(R.id.submit);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Request userId = OkHttpSingle.getUserId(UserInfo.getCookie());

                OkHttpSingle.getOkHttpClient().newCall(userId).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Message obtain = Message.obtain();
                        obtain.obj = false;
                        handler.sendMessage(obtain);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        try {
                            String string = response.body().string();
                            String s = regexUserId(string);
                            UserInfo.setId(s);
                            DatabaseUtils.save("userId", s);
                            Message obtain = Message.obtain();
                            obtain.obj = true;
                            handler.sendMessage(obtain);
                            finish();
                        } catch (Throwable e) {
                            Message obtain = Message.obtain();
                            obtain.obj = false;
                            handler.sendMessage(obtain);
                        }


                    }
                });
            }
        });


        go = AgentWeb.with(this)//传入Activity
                .setAgentWebParent(findViewById(R.id.m_layout), new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebViewClient(new MyWebViewClient())
                .createAgentWeb()
                .ready()
                .go(url1);

    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {

            if (url.equals(url1)) {
                try {
                    CookieManager instance = CookieManager.getInstance();
                    String cookie = instance.getCookie(url);
                    try {
                        cookie = regexCookie(cookie);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    UserInfo.setCookie(cookie);
                    DatabaseUtils.save("cookie", cookie);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            super.onPageFinished(view, url);

        }


    }


    private String regexCookie(String cookie) {
        String regex = "JSESSIONID=[^;]*";
        Pattern compile = Pattern.compile(regex);

        Matcher matcher = compile.matcher(cookie);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    private String regexUserId(String s) {
        String regex = "\"accountId\":[0-9]*";
        Pattern compile = Pattern.compile(regex);

        String res = null;

        Matcher matcher = compile.matcher(s);
        if (matcher.find()) {
            res = matcher.group();
        }

        res = getId(res);
        return res;
    }

    private String getId(String s) {
        String regex = ":[0-9]*";
        Pattern compile = Pattern.compile(regex);

        Matcher matcher = compile.matcher(s);
        if (matcher.find()) {
            return matcher.group().replace(":", "");
        }
        return null;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.clear_cache) {
            go.clearWebCache();
            Toast.makeText(this, "清除成功", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.m124308) {
           url1= UserInfo.url = "https://m.124308.com/login";
        }else if (itemId == R.id.m524229) {
            url1=  UserInfo.url = "https://m.524229.com/login";
        }else if (itemId == R.id.m828874) {
            url1=  UserInfo.url = "https://m.828874.com/login";
        }else if (itemId == R.id.m835340) {
            url1=   UserInfo.url = "https://m.835340.com/login";
        } else if (itemId == R.id.reload) {
            WebView webView = go.getWebCreator().getWebView();
            webView.reload();
            webView.loadUrl(url1);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


}

/*



 */
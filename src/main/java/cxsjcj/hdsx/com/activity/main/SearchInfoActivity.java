package cxsjcj.hdsx.com.activity.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cxsjcj.hdsx.com.activity.address.EditAddressActivity;
import cxsjcj.hdsx.com.activity.base.MyBaseActivity;
import cxsjcj.hdsx.com.activity.mylocation.MyLoationActivity;
import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.view.KeywordsFlow;

public class SearchInfoActivity extends MyBaseActivity implements View.OnClickListener {

    public static final String[] keywords = {"QQ", "Sodino", "APK", "GFW", "铅笔",//
            "短信", "桌面精灵", "MacBook Pro", "平板电脑", "雅诗兰黛",//
            "卡西欧 TR-100", "笔记本", "SPY Mouse", "Thinkpad E40", "捕鱼达人",//
            "内存清理", "地图", "导航", "闹钟", "主题",//
            "通讯录", "播放器", "CSDN leak", "安全", "3D",//
            "美女", "天气", "4743G", "戴尔", "联想",//
            "欧朋", "浏览器", "愤怒的小鸟", "mmShow", "网易公开课",//
            "iciba", "油水关系", "网游App", "互联网", "365日历",//
            "脸部识别", "Chrome", "Safari", "中国版Siri", "A5处理器",//
            "iPhone4S", "摩托 ME525", "魅族 M9", "尼康 S2500"};
    private KeywordsFlow keywordsFlow;
    private TextView btnIn;
    private LinearLayout ll_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("快速查询");
        toolbar.setBackgroundColor(getColorPrimary());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_settings:
                            startActivityForResult(new Intent(SearchInfoActivity.this, EditAddressActivity.class), 100);
                            break;
                    }
                    return true;
                }
            });
        }
        btnIn = (TextView) findViewById(R.id.button1);
        TextView tv_shopper = (TextView) findViewById(R.id.tv_shopper);
        tv_shopper.setOnClickListener(this);
        TextView tv_dp = (TextView) findViewById(R.id.tv_dp);
        TextView tv_location = (TextView) findViewById(R.id.tv_location);
        tv_dp.setOnClickListener(this);
        tv_location.setOnClickListener(this);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        btnIn.setOnClickListener(this);
        keywordsFlow = (KeywordsFlow) findViewById(R.id.frameLayout1);
        keywordsFlow.setDuration(800l);
        keywordsFlow.setOnItemClickListener(this);
        feedKeywordsFlow(keywordsFlow, keywords);
        keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
        UpdateUI();
    }

    private void UpdateUI() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                handler.sendMessage(message);
            }
        }, 5000, 5000);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            keywordsFlow.rubKeywords();
            // keywordsFlow.rubAllViews();
            feedKeywordsFlow(keywordsFlow, keywords);
            keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
        }
    };

    private static void feedKeywordsFlow(KeywordsFlow keywordsFlow, String[] arr) {
        Random random = new Random();
        for (int i = 0; i < KeywordsFlow.MAX; i++) {
            int ran = random.nextInt(arr.length);
            String tmp = arr[ran];
            keywordsFlow.feedKeyword(tmp);
        }
    }

    @Override
    protected boolean isopen() {
        return true;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_search_info;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == btnIn) {
            keywordsFlow.rubKeywords();
            // keywordsFlow.rubAllViews();
            feedKeywordsFlow(keywordsFlow, keywords);
            keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
        }
        if (v instanceof TextView) {
            String keyword = ((TextView) v).getText().toString();
            Log.e("Search", keyword);
            Toast.makeText(SearchInfoActivity.this, keyword, Toast.LENGTH_SHORT).show();
        }
        if (v.getId() == R.id.tv_shopper) {
            changeColor(R.id.tv_shopper);
        }
        if (v.getId() == R.id.tv_dp) {
            changeColor(R.id.tv_dp);
        }
        if (v.getId() == R.id.tv_location) {
            changeColor(R.id.tv_location);
        }
    }

    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    private void changeColor(int id) {
        for (int i = 0; i < ll_content.getChildCount(); i++) {
            View view = ll_content.getChildAt(i);
            if (view instanceof TextView) {
                if (view.getId() == id) {
                    view.setBackgroundColor(getColorPrimary());
                    ((TextView) view).setTextColor(Color.WHITE);
                } else {
                    view.setBackgroundColor(Color.WHITE);
                    ((TextView) view).setTextColor(Color.BLACK);
                }
            }
        }
    }
}

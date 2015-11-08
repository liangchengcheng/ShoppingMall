package cxsjcj.hdsx.com.activity.welcom;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Toast;

import cxsjcj.hdsx.com.activity.login.DefaultIntro;
import cxsjcj.hdsx.com.activity.main.MainActivity;
import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.view.ShimmerFrameLayout;

/**
 * @author: 梁铖城
 * @date: 2015年8月27日22:47:09
 * @description: 欢迎的界面，可以餐开下掌阅的设计，制作出一个好看的欢迎界面
 * @blog: http:www.17yxb.cn
 */
public class WelComePage extends ActionBarActivity {
    private ShimmerFrameLayout mShimmerViewContainer;
    private int mCurrentPreset = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wel_come_page);
        mShimmerViewContainer = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        startAnimation();
    }
    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }
    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }
    private void startAnimation() {
        AnimationSet set = new AnimationSet(false);
        AlphaAnimation alpha = new AlphaAnimation(1, 1);
        alpha.setDuration(4000);
        alpha.setFillAfter(true);
        set.addAnimation(alpha);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                JumpNextPage();
            }
        });
        findViewById(R.id.rl_welcombg).startAnimation(set);
    }
    public void JumpNextPage() {
        SharedPreferences preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        if (preferences.getBoolean("firststart", true)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firststart", false);
            editor.apply();
            Intent intent = new Intent();
            intent.setClass(WelComePage.this, DefaultIntro.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent();
            intent.setClass(WelComePage.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

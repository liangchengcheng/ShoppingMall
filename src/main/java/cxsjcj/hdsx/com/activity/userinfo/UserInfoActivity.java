package cxsjcj.hdsx.com.activity.userinfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;
import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.view.CardViewPlus;
import cxsjcj.hdsx.com.view.CustomViewPager;

import cxsjcj.hdsx.com.view.RevealBackgroundView;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年10月14日23:01:38
 * Description:   个人中心的界面
 */

public class UserInfoActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener, RevealBackgroundView.OnStateChangeListener {
    private TabLayout tlUserProfileTabs;
    private CustomViewPager viewPager;
    RevealBackgroundView vRevealBackground;
    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    private static final int USER_OPTIONS_ANIMATION_DELAY = 300;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();
    private FloatingActionButton btnCreate;

    View vUserProfileRoot;
    ImageView ivUserProfilePhoto;
    View vUserDetails;
    View vUserStats;
    public static void startUserInfoActivity(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, UserInfoActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);
        btnCreate= (FloatingActionButton) findViewById(R.id.btnCreate);
        //mContentView= (CardViewPlus) findViewById(R.id.mContentView);
        vRevealBackground= (RevealBackgroundView) findViewById(R.id.vRevealBackground);
        vUserProfileRoot = findViewById(R.id.vUserProfileRoot);
        ivUserProfilePhoto = (ImageView) findViewById(R.id.ivUserProfilePhoto);
        vUserStats = findViewById(R.id.vUserStats);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // toolbar.setBackgroundColor(getColorPrimary());
        setSupportActionBar(toolbar);
        toolbar.setTitle("基本资料");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tlUserProfileTabs = (TabLayout) findViewById(R.id.tlUserProfileTabs);
        vUserDetails = findViewById(R.id.vUserDetails);
        setupTabs();
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        //getViewTreeObserver().addOnGlobalLayoutListener(this);
        setupRevealBackground(savedInstanceState);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());

        adapter.addFragment(new UserDataFragment());
        adapter.addFragment(new UserIntegrityFragment());
        adapter.addFragment(new UserInfoConnectionFragment());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    private void setupTabs() {
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_grid_on_white));
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_list_white));
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_place_white));
        tlUserProfileTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    viewPager.setCurrentItem(0);
                    btnCreate.setVisibility(View.VISIBLE);
                } else if (tab.getPosition() == 1) {
                    viewPager.setCurrentItem(1);
                    btnCreate.setVisibility(View.VISIBLE);
                } else {
                    viewPager.setCurrentItem(2);
                    btnCreate.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    static class Adapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragments = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }

    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGlobalLayout() {
        if (Build.VERSION.SDK_INT >= 16) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else {
            getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }

        startRevealTransition();
    }

    private CardViewPlus mContentView;

    protected View getRootView() {
        return mContentView;
    }

    protected ViewTreeObserver getViewTreeObserver() {
        return getRootView().getViewTreeObserver();
    }

    private SupportAnimator.SimpleAnimatorListener mCard2AnimatorListener = new SupportAnimator.SimpleAnimatorListener() {
        @Override
        public void onAnimationEnd() {

            setupRevealBackground(null);
        }
    };

    protected void startRevealTransition() {
        final Rect bounds = new Rect();
        getRootView().getHitRect(bounds);
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(getRootView(),
                bounds.left, bounds.top, 0, hypo(bounds.height(), bounds.width()));
        animator.addListener(mCard2AnimatorListener);
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    public static float hypo(float a, float b) {
        return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

    private void setupRevealBackground(Bundle savedInstanceState) {

        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {
            vRevealBackground.setToFinishedFrame();
            //userPhotosAdapter.setLockedAnimations(true);
        }
    }


    @Override
    public void onStateChange(int state) {
        Log.e("lcc", state + "");
        if (RevealBackgroundView.STATE_FINISHED == state) {
            viewPager.setVisibility(View.VISIBLE);
            tlUserProfileTabs.setVisibility(View.VISIBLE);
            vUserProfileRoot.setVisibility(View.VISIBLE);
            // userPhotosAdapter = new UserProfileAdapter(this);
            // rvUserProfile.setAdapter(userPhotosAdapter);
            animateUserProfileOptions();
            animateUserProfileHeader();
        }
        else {
            tlUserProfileTabs.setVisibility(View.INVISIBLE);
            viewPager.setVisibility(View.INVISIBLE);
            vUserProfileRoot.setVisibility(View.INVISIBLE);
        }
    }

    private void animateUserProfileOptions() {
        tlUserProfileTabs.setTranslationY(-tlUserProfileTabs.getHeight());
        tlUserProfileTabs.animate().translationY(0).setDuration(300).setStartDelay(USER_OPTIONS_ANIMATION_DELAY).setInterpolator(INTERPOLATOR);
    }

    private void animateUserProfileHeader() {
        vUserProfileRoot.setTranslationY(-vUserProfileRoot.getHeight());
        ivUserProfilePhoto.setTranslationY(-ivUserProfilePhoto.getHeight());
        vUserDetails.setTranslationY(-vUserDetails.getHeight());
        vUserStats.setAlpha(0);

        vUserProfileRoot.animate().translationY(0).setDuration(300).setInterpolator(INTERPOLATOR);
        ivUserProfilePhoto.animate().translationY(0).setDuration(300).setStartDelay(100).setInterpolator(INTERPOLATOR);
        vUserDetails.animate().translationY(0).setDuration(300).setStartDelay(200).setInterpolator(INTERPOLATOR);
        vUserStats.animate().alpha(1).setDuration(200).setStartDelay(400).setInterpolator(INTERPOLATOR).start();
    }
}

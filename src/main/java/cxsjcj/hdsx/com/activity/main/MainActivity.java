package cxsjcj.hdsx.com.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.ThemeSingleton;

import java.util.ArrayList;
import java.util.List;

import cxsjcj.hdsx.com.activity.about.AboutActivity;
import cxsjcj.hdsx.com.activity.address.EditAddressMainActivity;
import cxsjcj.hdsx.com.activity.base.MyBaseActivity;
import cxsjcj.hdsx.com.activity.login.LoginActivity;
import cxsjcj.hdsx.com.activity.mylocation.MyLoationActivity;
import cxsjcj.hdsx.com.activity.order.BuyOrderActivity;
import cxsjcj.hdsx.com.activity.order.DealOrderActivity;
import cxsjcj.hdsx.com.activity.setting.SysTemSettingActivity;
import cxsjcj.hdsx.com.activity.shoppingcar.ShoppingCarActivity;
import cxsjcj.hdsx.com.activity.userinfo.UserChangeActivity;
import cxsjcj.hdsx.com.activity.userinfo.UserInfoActivity;
import cxsjcj.hdsx.com.activity.userinfo.WallectActivity;
import cxsjcj.hdsx.com.fragment.ContentMenuFragment;
import cxsjcj.hdsx.com.fragment.DealeristFragment;
import cxsjcj.hdsx.com.fragment.HomeFragment;
import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.test.listanimation.Main3Activity;
import cxsjcj.hdsx.com.utils.PreferenceUtils;
import cxsjcj.hdsx.com.utils.SingleTrueUtils;
import cxsjcj.hdsx.com.utils.UpDateApkUtils;
import cxsjcj.hdsx.com.view.ColorChooserDialog;
import cxsjcj.hdsx.com.view.floatingactionbutton.FloatingActionButton;
import cxsjcj.hdsx.com.view.floatingactionbutton.FloatingActionsMenu;
import de.greenrobot.event.EventBus;
import materialshowcaseview.MaterialShowcaseSequence;
import materialshowcaseview.ShowcaseConfig;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date: 2015年10月16日15:26:33
 * Description: 系统的主界面
 */

public class MainActivity extends MyBaseActivity implements ColorChooserDialog.Callback {
    DrawerLayout mDrawerLayout;
    FloatingActionButton AddButton, ShareButton, QihuanButton;
    static int selectedColorIndex = -1;
    private FloatingActionsMenu multiple_actions;
    private static final String SHOWCASE_ID = "sequence example";
    private Toolbar toolbar;
    private View headview;
    protected PreferenceUtils preferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferenceUtils = PreferenceUtils.getInstance(this);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        SharedPreferences sp = getSharedPreferences("lcc", Context.MODE_PRIVATE);
        String localtime = SingleTrueUtils.singlechou(System.currentTimeMillis());
        String savetime = sp.getString("updatetime", null);
        if (savetime == null || (Integer.parseInt(localtime) - Integer.parseInt(savetime)) / (60 * 60 * 24 * 7) > 7) {
            UpDateApkUtils upDateApkUtils = new UpDateApkUtils(MainActivity.this);
            upDateApkUtils.upDataAPK();
        }

        multiple_actions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        headview = findViewById(R.id.headview);
        headview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int[] startingLocation = new int[2];
                headview.getLocationOnScreen(startingLocation);
                startingLocation[0] += headview.getWidth() / 2;
                UserInfoActivity.startUserInfoActivity(startingLocation, MainActivity.this);
                overridePendingTransition(0, 0);
            }
        });
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        setupDrawerContent(navigationView);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        AddButton = (FloatingActionButton) findViewById(R.id.action_a);
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new ColorChooserDialog().show(MainActivity.this, selectedColorIndex);
                startActivity(new Intent(MainActivity.this, SearchInfoActivity.class));

            }
        });
        ShareButton = (FloatingActionButton) findViewById(R.id.action_b);
        ShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Main3Activity.class));
            }
        });
        QihuanButton = (FloatingActionButton) findViewById(R.id.action_q);
        QihuanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MyLoationActivity.class));
            }
        });
        //  ShareButton.setOnClickListener(this);
        //  ShareButton.setTitle("分享");
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        //tabLayout.setTabTextColors(Color.WHITE, Color.rgb(0, 150, 136));
        tabLayout.setupWithViewPager(viewPager);
        //设置文本在选中和为选中时候的颜色
        //下面的第一句话是完成引导的复原
        //MaterialShowcaseView.resetSingleUse(this, SHOWCASE_ID);
        //presentShowcaseSequence();
    }

    @Override
    protected boolean isopen() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }

    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            //去我的头像的那个界面
                            case R.id.nav_home:
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                break;

                            //去收货地址的主界面
                            case R.id.nav_address:
                                startActivity(new Intent(MainActivity.this, EditAddressMainActivity.class));
                                break;

                            //去我的钱包的主界面
                            case R.id.nav_messages:
                                startActivity(new Intent(MainActivity.this, WallectActivity.class));
                                break;

                            //去系统设置的主机面
                            case R.id.sys_setting:
                                startActivity(new Intent(MainActivity.this, SysTemSettingActivity.class));
                                break;

                            //去订单之我是买家
                            case R.id.nav_friends:
                                startActivity(new Intent(MainActivity.this, BuyOrderActivity.class));
                                break;
                            //去订单之我是卖家
                            case R.id.nav_discussion:
                                startActivity(new Intent(MainActivity.this, DealOrderActivity.class));
                                break;
                            //购物车的界面
                            case R.id.nav_shoppingcar:
                                startActivity(new Intent(MainActivity.this, ShoppingCarActivity.class));
                                break;
                        }
                        menuItem.setChecked(true);//点击了把它设为选中状态
                        mDrawerLayout.closeDrawers();//关闭抽屉
                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        if (item.getItemId() == R.id.action_inbox) {
            startActivity(new Intent(MainActivity.this, UserChangeActivity.class));
            return true;
        }
        if (item.getItemId() == R.id.sync) {
            startActivity(new Intent(MainActivity.this, MainJBActivityActivity.class));
            return true;
        }
        if (item.getItemId() == R.id.about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
            return true;
        }
        if (item.getItemId() == R.id.action_shoppingcar) {
            startActivity(new Intent(MainActivity.this, CaptureActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "快速导航");
        adapter.addFragment(new ContentMenuFragment(), "热卖中心");
        adapter.addFragment(new DealeristFragment(), "金牌商家");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onColorSelection(int index, int color, int darker) {
        selectedColorIndex = index;
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        ThemeSingleton.get().positiveColor = color;
        ThemeSingleton.get().neutralColor = color;
        ThemeSingleton.get().negativeColor = color;
        ThemeSingleton.get().widgetColor = color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(darker);
            getWindow().setNavigationBarColor(color);
        }
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
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
            return mFragmentTitles.get(position);
        }
    }

    private void presentShowcaseSequence() {
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, SHOWCASE_ID);
        sequence.setConfig(config);
        sequence.addSequenceItem(multiple_actions,
                "点击下面的按钮展开，可以快速发布信息", "下一步");
        sequence.addSequenceItem(toolbar,
                "点击可以切换到不同的频道", "完成引导");
        sequence.start();
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEvent(Integer event) {
        switch (event) {
            case 0x02:
                this.recreate();
                if (mDrawerLayout != null) {
                    mDrawerLayout.closeDrawers();
                }
                break;
        }
    }

}

package cxsjcj.hdsx.com.activity.setting;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;

import cxsjcj.hdsx.com.activity.base.MyBaseActivity;
import cxsjcj.hdsx.com.adapter.ColorsListAdapter;
import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.utils.PreferenceUtils;
import cxsjcj.hdsx.com.utils.ThemeUtils;
import de.greenrobot.event.EventBus;

public class SysTemSettingActivity extends MyBaseActivity implements View.OnClickListener {

    private LinearLayout ll_zan;
    protected PreferenceUtils preferenceUtils;
    protected MyBaseActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceUtils = PreferenceUtils.getInstance(SysTemSettingActivity.this);
        activity = (MyBaseActivity)SysTemSettingActivity.this;
        ll_zan = (LinearLayout) findViewById(R.id.ll_zan);
        ll_zan.setOnClickListener(this);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getColorPrimary());
        toolbar.setTitle("系统设置");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        findViewById(R.id.tx_changetheme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showThemeDialog();
            }
        });
    }

    @Override
    protected boolean isopen() {
        return true;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_sys_tem_setting;
    }

    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_zan:
                PopupMenu popup = new PopupMenu(SysTemSettingActivity.this, ll_zan);
                popup.getMenuInflater()
                        .inflate(R.menu.menu_main, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        return true;
                    }
                });
                popup.show();
                break;
        }
    }

    private void showThemeDialog() {
        AlertDialog.Builder builder = generateDialogBuilder();
        builder.setTitle(R.string.change_theme);
        Integer[] res = new Integer[]{R.drawable.red_round, R.drawable.brown_round, R.drawable.blue_round,
                R.drawable.blue_grey_round, R.drawable.yellow_round, R.drawable.deep_purple_round,
                R.drawable.pink_round, R.drawable.green_round};
        List<Integer> list = Arrays.asList(res);
        ColorsListAdapter adapter = new ColorsListAdapter(SysTemSettingActivity.this, list);
        adapter.setCheckItem(getCurrentTheme().getIntValue());
        GridView gridView = (GridView) LayoutInflater.from(SysTemSettingActivity.this).inflate(R.layout.colors_panel_layout, null);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setCacheColorHint(0);
        gridView.setAdapter(adapter);
        builder.setView(gridView);
        final AlertDialog dialog = builder.show();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                int value = getCurrentTheme().getIntValue();
                if (value != position) {
                    preferenceUtils.saveParam(getString(R.string.change_theme_key), position);
                    changeTheme(ThemeUtils.Theme.mapValueToTheme(position));
                }
            }
        });
    }

    protected AlertDialog.Builder generateDialogBuilder() {
        ThemeUtils.Theme theme = getCurrentTheme();
        AlertDialog.Builder builder;
        switch (theme) {
            case BROWN:
                builder = new AlertDialog.Builder(SysTemSettingActivity.this, R.style.BrownDialogTheme);
                break;
            case BLUE:
                builder = new AlertDialog.Builder(SysTemSettingActivity.this, R.style.BlueDialogTheme);
                break;
            case BLUE_GREY:
                builder = new AlertDialog.Builder(SysTemSettingActivity.this, R.style.BlueGreyDialogTheme);
                break;
            default:
                builder = new AlertDialog.Builder(SysTemSettingActivity.this, R.style.RedDialogTheme);
                break;
        }
        return builder;
    }

    private void changeTheme(ThemeUtils.Theme theme) {
        if (activity == null)
            return;
        EventBus.getDefault().post(0x02);
        activity.finish();
    }
}

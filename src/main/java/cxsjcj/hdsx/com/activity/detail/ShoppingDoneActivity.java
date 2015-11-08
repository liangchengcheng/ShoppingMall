package cxsjcj.hdsx.com.activity.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;

import cxsjcj.hdsx.com.activity.base.MyBaseActivity;
import cxsjcj.hdsx.com.adapter.HomeGridAdapter;
import cxsjcj.hdsx.com.adapter.HomeTestGridAdapter;
import cxsjcj.hdsx.com.itemanimation.FadeInAnimator;
import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.utils.FullyGridLayoutManager;
import cxsjcj.hdsx.com.utils.FullyGridLayoutManagers;


/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date: 2015年10月22日22:31:54
 * Description:  完成了订单支付之后的界面
 */

public class ShoppingDoneActivity extends MyBaseActivity {

    private static String[] data = new String[]{
            "泉哥的内裤", "泉哥的胸罩", "泉哥的娃娃", "泉哥的纸巾"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getColorPrimary());
        toolbar.setTitle("提交订单");
        toolbar.setTitleTextColor(getColor(R.color.action_bar_title_color));
        toolbar.collapseActionView();
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        FullyGridLayoutManager manager=new FullyGridLayoutManager(ShoppingDoneActivity.this, 2);
        recyclerView.setLayoutManager(manager);
        HomeTestGridAdapter adapter = new HomeTestGridAdapter(ShoppingDoneActivity.this,
                new ArrayList<>(Arrays.asList(data)));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected boolean isopen() {
        return true;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_shopping_done;
    }

    protected int getColor(int res){
        if (res <= 0)
            throw new IllegalArgumentException("resource id can not be less 0");
        return getResources().getColor(res);
    }
    public int getColorPrimary(){
        TypedValue typedValue = new  TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping_done, menu);
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
}

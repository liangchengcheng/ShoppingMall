package cxsjcj.hdsx.com.activity.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cxsjcj.hdsx.com.activity.base.MyBaseActivity;
import cxsjcj.hdsx.com.myapplication.R;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年10月15日10:23:15
 * Description:  关于订单
 */

// TODO: 2015/10/14 进度：40%，格式：合格，注意：无 ，未完成模块：无  
public class ShoppingTrueActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initUI();

    }
    private void initToolbar(){
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getColorPrimary());
        toolbar.setTitle("确认订单");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected boolean isopen() {
        return true;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_shopping_true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shopping_true, menu);
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

    //初始化控件和点击事件
    private void initUI(){
        TextView ok= (TextView) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(ShoppingTrueActivity.this,ShoppingDoneActivity.class));
            }
        });
    }
}

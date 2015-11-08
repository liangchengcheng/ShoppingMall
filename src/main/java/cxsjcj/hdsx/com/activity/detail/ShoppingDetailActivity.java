package cxsjcj.hdsx.com.activity.detail;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import cxsjcj.hdsx.com.activity.base.MyBaseActivity;
import cxsjcj.hdsx.com.fragment.VerticalFragment1;
import cxsjcj.hdsx.com.fragment.VerticalFragment3;
import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.view.DragLayout;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:  2015年10月25日15:58:09
 * Description:  商品详情界面：带下滑加载到下面的详情的界面
 */

public class ShoppingDetailActivity extends MyBaseActivity {

    private VerticalFragment1 fragment1;
    private VerticalFragment3 fragment3;
    private DragLayout draglayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getColorPrimary());
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getColor(R.color.action_bar_title_color));
        toolbar.collapseActionView();
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    @Override
    protected boolean isopen() {
        return true;
    }

    //初始化一个view
    private void initView() {
        fragment1 = new VerticalFragment1();
        fragment3 = new VerticalFragment3();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.first, fragment1).add(R.id.second, fragment3)
                .commit();

        DragLayout.ShowNextPageNotifier nextIntf = new DragLayout.ShowNextPageNotifier() {
            @Override
            public void onDragNext() {
                fragment3.initView();
            }
        };
        draglayout = (DragLayout) findViewById(R.id.draglayout);
        draglayout.setNextPageListener(nextIntf);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_shopping_detail;
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
        getMenuInflater().inflate(R.menu.menu_shopping_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

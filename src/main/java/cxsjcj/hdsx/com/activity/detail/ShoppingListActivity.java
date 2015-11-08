package cxsjcj.hdsx.com.activity.detail;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import cxsjcj.hdsx.com.activity.address.EditAddressActivity;
import cxsjcj.hdsx.com.activity.address.EditAddressMainActivity;
import cxsjcj.hdsx.com.activity.base.MyBaseActivity;
import cxsjcj.hdsx.com.adapter.CategoryAdapter;
import cxsjcj.hdsx.com.adapter.ContentAdapter;
import cxsjcj.hdsx.com.bean.Product;
import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.view.MyListView;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年11月2日15:06:45
 * Description: 双列的listview展示商品列表的界面
 */

public class ShoppingListActivity extends MyBaseActivity implements AdapterView.OnItemClickListener{

    public static final String TAG = "MainActivity";

    private CategoryAdapter mCategoryAdapter;
    private MyListView mMenuListView;
    private MyListView mContentListView;
    private static final String[] mMenus =
            {"常用分类", "服饰内衣", "鞋靴", "手机", "家用电器", "数码",
                    "个护化妆", "图书", "鞋靴", "手机", "家用电器", "数码", "电脑办公"};
    private List<Product> mDatas=new ArrayList<Product>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("商品列表");
        toolbar.setBackgroundColor(getColorPrimary());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        startActivityForResult(new Intent(ShoppingListActivity.this, EditAddressActivity.class), 100);
                        break;
                }
                return true;
            }
        });

        mMenuListView = (MyListView) findViewById(R.id.menu_list);
        mContentListView = (MyListView) findViewById(R.id.menu_list_content);

        mCategoryAdapter = new CategoryAdapter(this, mMenus);
        mMenuListView.setAdapter(mCategoryAdapter);

        mMenuListView.setOnItemClickListener(this);
        mCategoryAdapter.setViewBackGround(0);
        smoothScroollListView(0);
    }

    @Override
    protected boolean isopen() {
        return true;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_shopping_list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping_list, menu);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        smoothScroollListView(position);
        mCategoryAdapter.setViewBackGround(position);
        mCategoryAdapter.notifyDataSetChanged();
    }

    /**
     * listView scroll
     * @param position
     */
    public void smoothScroollListView(int position){
        if (Build.VERSION.SDK_INT >= 21) {
            mMenuListView.setSelectionFromTop(position,0);
        } else if (Build.VERSION.SDK_INT >= 11) {
            mMenuListView.smoothScrollToPositionFromTop(position,0,500);
        } else if (Build.VERSION.SDK_INT >= 8) {
            int firstVisible = mMenuListView.getFirstVisiblePosition();
            int lastVisible = mMenuListView.getLastVisiblePosition();

            Log.i(TAG, " firstVisible " + firstVisible + " lastVisible " + lastVisible + "  position " + position);

            if (position < firstVisible) {
                mMenuListView.smoothScrollToPosition(position);
            } else {
                if (firstVisible == 0) {
                    mMenuListView.smoothScrollToPosition(position + lastVisible - firstVisible);
                } else {
                    mMenuListView.smoothScrollToPosition(position + lastVisible - firstVisible - 1);
                }
            }
        } else {
            mMenuListView.setSelection(position);
        }

        mDatas=new ArrayList<>();
        Product product=new Product(R.drawable.w1,"丝袜");
        mDatas.add(product);
        Product product1=new Product(R.drawable.w2,"打底裤");
        mDatas.add(product1);
        Product product2=new Product(R.drawable.w3,"连衣裙");
        mDatas.add(product2);
        Product product3=new Product(R.drawable.w4,"毛衣");
        mDatas.add(product3);
        Product product4=new Product(R.drawable.w5,"针织衫");
        mDatas.add(product4);
        Product product5=new Product(R.drawable.w6,"牛仔裤");
        mDatas.add(product5);
        Product product6=new Product(R.drawable.w7,"衬衫");
        mDatas.add(product6);

        Product product7=new Product(R.drawable.w8,"衬衫");
        mDatas.add(product7);

        mDatas.add(product7);
        mDatas.add(product7);
//      下面注释的地方是演示的模拟数据 mMenus[position] 来进行数据的改变
//        String[] items = new String[(position + 1) * 2];
//        for (int i = 0; i < items.length; i++) {
//            items[i] = mMenus[position] + "中的数据：" + i;
//        }

        mContentListView.setAdapter(new ContentAdapter(ShoppingListActivity.this, mDatas));
    }
}

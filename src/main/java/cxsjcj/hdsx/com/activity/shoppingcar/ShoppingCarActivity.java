package cxsjcj.hdsx.com.activity.shoppingcar;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;
import com.itangqi.greendao.NoteDao;

import java.util.List;

import cxsjcj.hdsx.com.activity.base.MyBaseActivity;
import cxsjcj.hdsx.com.adapter.ShoppingCarAdapter;
import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.myapplication.NetApplication;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class ShoppingCarActivity extends MyBaseActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("购物车");
        toolbar.setBackgroundColor(getColorPrimary());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                }
                return true;
            }
        });
        search();
    }

    @Override
    protected boolean isopen() {
        return true;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_shopping_car;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    private void search() {
        // Query 类代表了一个可以被重复执行的查询
        Query query = getNoteDao().queryBuilder()
                .build();
        // 查询结果以 List 返回
        List notes = query.list();
        if (notes == null || notes.size() == 0) {
            new MaterialDialog.Builder(ShoppingCarActivity.this).iconRes(R.drawable.ic_launcher)
                    .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                    .title("购物车")
                    .icon(ShoppingCarActivity.this.getResources().getDrawable(R.drawable.cheese_1))
                    .content("你当前的购物车是空的，先去逛逛吧")
                    .positiveText("好的")
                    .negativeText("取消")
                    .cancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {

                        }
                    })
                    .dismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {

                        }
                    })
                    .show();
        }
        ShoppingCarAdapter adapter = new ShoppingCarAdapter(ShoppingCarActivity.this, notes);
        recyclerView.setAdapter(adapter);
        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    private NoteDao getNoteDao() {
        // 通过 BaseApplication 类提供的 getDaoSession() 获取具体 Dao
        return ((NetApplication) this.getApplicationContext()).getDaoSession().getNoteDao();
    }

    private SQLiteDatabase getDb() {
        // 通过 BaseApplication 类提供的 getDb() 获取具体 db
        return ((NetApplication) this.getApplicationContext()).getDb();
    }
}

package cxsjcj.hdsx.com.activity.about;

import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import cxsjcj.hdsx.com.activity.base.MyBaseActivity;
import cxsjcj.hdsx.com.adapter.MaterialSimpleListAdapter;
import cxsjcj.hdsx.com.bean.MaterialSimpleListItem;
import cxsjcj.hdsx.com.myapplication.R;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:   2015年10月14日23:21:40
 * Description:  关于界面
 */
public class AboutActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("关于");
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
    }

    @Override
    protected boolean isopen() {
        return true;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_about;
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
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                showShareDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showShareDialog(){
        AlertDialog.Builder builder = generateDialogBuilder();
        builder.setTitle(getString(R.string.share));
        final MaterialSimpleListAdapter adapter = new MaterialSimpleListAdapter(this);
        String[] array = getResources().getStringArray(R.array.share_dialog_text);
        adapter.add(new MaterialSimpleListItem.Builder(this)
                .content(array[0])
                .icon(R.drawable.ic_wx_logo)
                .build());
        adapter.add(new MaterialSimpleListItem.Builder(this)
                .content(array[1])
                .icon(R.drawable.ic_wx_moments)
                .build());
        adapter.add(new MaterialSimpleListItem.Builder(this)
                .content(array[2])
                .icon(R.drawable.ic_wx_collect)
                .build());
        adapter.add(new MaterialSimpleListItem.Builder(this)
                .content(array[3])
                .icon(R.drawable.ic_sina_logo)
                .build());
        adapter.add(new MaterialSimpleListItem.Builder(this)
                .content(array[4])
                .icon(R.drawable.ic_share_more)
                .build());
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;

                }
            }
        });
        builder.show();
    }

}

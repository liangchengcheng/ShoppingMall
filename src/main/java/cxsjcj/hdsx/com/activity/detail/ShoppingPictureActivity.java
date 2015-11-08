package cxsjcj.hdsx.com.activity.detail;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;

import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.view.MyPhotoView;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年10月25日16:13:46
 * Description:   关于图片具体描述的界面
 */
public class ShoppingPictureActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_picture);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getColorPrimary());toolbar.setTitle("图片详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Intent intent = getIntent();
        String iamgeurl =  intent.getStringExtra("image");
        MyPhotoView iv_head= (MyPhotoView) findViewById(R.id.iv_head);
        if (iamgeurl != null) {
            Glide.with(ShoppingPictureActivity.this)
                    .load(iamgeurl)
                    .centerCrop()
                    .placeholder(R.drawable.loading1)
                    .crossFade()
                    .into(iv_head);
        }
    }

    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shopping_picture, menu);
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
}

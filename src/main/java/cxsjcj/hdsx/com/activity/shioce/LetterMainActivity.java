package cxsjcj.hdsx.com.activity.shioce;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rey.material.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import cxsjcj.hdsx.com.activity.address.EditAddressActivity;
import cxsjcj.hdsx.com.activity.address.EditAddressMainActivity;
import cxsjcj.hdsx.com.activity.base.MyBaseActivity;
import cxsjcj.hdsx.com.activity.main.MainActivity;
import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.utils.ToastUtils;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年10月31日17:51:15
 * Description:  城市选择的界面
 */

// TODO: 2015/10/31 需要注意的是：这个界面并没有用上。如果你想看这个界面请直接在固定的按钮处添加点击事件。然后startactivity
public class LetterMainActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("选择城市");
        toolbar.setBackgroundColor(getColorPrimary());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        startActivityForResult(new Intent(LetterMainActivity.this, EditAddressActivity.class), 100);
                        break;
                }
                return true;
            }
        });
        LetterListView letterListView = (LetterListView) findViewById(R.id.letterListView);
        final TestAdapter adapter = new TestAdapter();
        letterListView.setAdapter(adapter);
        letterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                Intent intent = new Intent(LetterMainActivity.this,
                        LetterChoice.class);
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected boolean isopen() {
        return true;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.letter_main;
    }

    protected int getColor(int res) {
        if (res <= 0)
            throw new IllegalArgumentException("resource id can not be less 0");
        return getResources().getColor(res);
    }

    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    class TestAdapter extends LetterBaseListAdapter<NameValuePair> {
        private static final String LETTER_KEY = "letter";
        String[] dataArray = {"安徽大区", "北京大区", "重庆大区", "东北大区", "福建大区", "广西大区", "广东大区", "湖南大区", "湖北大区", "河北大区", "河南大区", "华北大区", "黑龙江大区", "江苏大区", "江西大区",
                "辽宁大区", "上海大区", "四川大区", "山东大区", "陕西大区",
                "山西大区", "西北大区", "西南大区", "云南大区", "浙江大区"
        };

        public TestAdapter() {
            super();
            List<NameValuePair> dataList = new ArrayList<NameValuePair>();
            for (int i = 0; i < dataArray.length; i++) {
                NameValuePair pair = new BasicNameValuePair(String.valueOf(i),
                        dataArray[i]);
                dataList.add(pair);
            }
            setContainerList(dataList);
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public String getItemString(NameValuePair t) {
            return t.getValue();
        }

        @Override
        public NameValuePair create(char letter) {
            return new BasicNameValuePair(LETTER_KEY, String.valueOf(letter));
        }

        @Override
        public boolean isLetter(NameValuePair t) {
            // 判断是不是字母行,通过key比较,这里是NameValuePair对象,其他对象,就由你自己决定怎
            return t.getName().equals(LETTER_KEY);
        }

        @Override
        public View getLetterView(int position, View convertView,
                                  ViewGroup parent) {
            // 这里是字母的item界面设置.
            if (convertView == null) {
                convertView = new TextView(LetterMainActivity.this);
                ((TextView) convertView).setGravity(Gravity.CENTER_VERTICAL);
//                convertView.setBackgroundColor(getResources().getColor
//                        (android.R.color.white));
            }
            ((TextView) convertView).setText(list.get(position).getValue());
            ((TextView) convertView).setTextSize(16);

            return convertView;
        }

        @Override
        public View getContainerView(int position, View convertView,
                                     ViewGroup parent) {
            // 这里是其他正常数据的item界面设置.
            if (convertView == null) {
                convertView = new TextView(LetterMainActivity.this);
                convertView.setBackgroundColor(getResources().getColor
                        (android.R.color.white));
                ((TextView) convertView).setGravity(Gravity.CENTER_VERTICAL);
            }
            ((TextView) convertView).setText(list.get(position).getValue());

            return convertView;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            new MaterialDialog.Builder(this)
                    .title("你确定这个当做你的默认的服务器的么？")
                    .content("即将把  " + data.getStringExtra("result") + "  当做你的默认的服务器，下次进入的时候讲默认给你推荐这个服务器的相关的资源，如需修改点击右下角的图标修改。")
                    .positiveText("确定")
                    .negativeText("取消")
                    .cancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            ToastUtils.showToast(LetterMainActivity.this, "取消");
                        }
                    })
                    .dismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            ToastUtils.showToast(LetterMainActivity.this, "onDismiss");
                            Intent intent=new Intent();
                            intent.putExtra("result","0");
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    })
                    .show();
        }
    }

}

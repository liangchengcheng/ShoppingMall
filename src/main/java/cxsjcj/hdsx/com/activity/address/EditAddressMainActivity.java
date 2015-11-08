package cxsjcj.hdsx.com.activity.address;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cxsjcj.hdsx.com.activity.base.MyBaseActivity;
import cxsjcj.hdsx.com.adapter.AddressMainAdapter;
import cxsjcj.hdsx.com.bean.AddressBean;
import cxsjcj.hdsx.com.contacts.CodeContacts;
import cxsjcj.hdsx.com.itemanimation.CustomItemAnimator;
import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.network.okhttp.OkHttpClientManager;
import cxsjcj.hdsx.com.utils.Base64;
import cxsjcj.hdsx.com.utils.Md5Utils;
import cxsjcj.hdsx.com.utils.SingleTrueUtils;



/**
 * @author: 梁铖城
 * @date: 2015年8月29日17:52:23
 * @description: 收货地址的管理的界面
 * @blog: http:www.17yxb.cn
 */
public class EditAddressMainActivity extends MyBaseActivity {
    private static final String mTAG = "TagTwo";
    private List<AddressBean> linkmans = new ArrayList<>();
    private RecyclerView recyclerView;
    private AddressMainAdapter adapter;
    private ProgressWheel progress_wheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progress_wheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("收货地址");
        toolbar.setBackgroundColor(getColorPrimary());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        startActivityForResult(new Intent(EditAddressMainActivity.this, EditAddressActivity.class), 100);
                        break;
                }
                return true;
            }
        });
        getUsers();
    }

    @Override
    protected boolean isopen() {
        return true;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_edit_address_main;
    }

    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            getUsers();
        }
    }

    public abstract class MyResultCallback<T> extends OkHttpClientManager.ResultCallback<T> {

        @Override
        public void onBefore() {
            super.onBefore();
        }

        @Override
        public void onAfter() {
            super.onAfter();
        }
    }

    public void getUsers() {
        try {
            long time = System.currentTimeMillis();
            String timechou = time + "";
            timechou = timechou.substring(0, timechou.length() - 3);
            Map<String, String> params = new HashMap<>();
            params.put("phone", "18813149871");
            JSONObject jsonRequest = new JSONObject();
            if (params != null) {
                for (String key : params.keySet()) {
                    jsonRequest.put(key, params.get(key));
                }
            }
            StringBuffer sb = new StringBuffer();
            sb.append(SingleTrueUtils.singltime(time));
            sb.append("18813149871");
            sb.append(Md5Utils.encode("5525682a"));
            String md5str = Md5Utils.encode(sb.toString().toUpperCase()).toUpperCase();

            String base = Base64.encode(md5str.getBytes());
            OkHttpClientManager.postAsyn("http://115.28.2.207:8001/Interface/AddressInfo.ashx?action=get&Calldate=" + timechou,
                    new OkHttpClientManager.Param[]{
                            new OkHttpClientManager.Param("CallUser", "18813149871"),
                            new OkHttpClientManager.Param("callValue", jsonRequest.toString()),
                            new OkHttpClientManager.Param("CallSiganture", base)
                    },
                    new MyResultCallback<String>() {
                        @Override
                        public void onError(Request request, Exception e) {
                            progress_wheel.setVisibility(View.GONE);
                            Log.e("okhttp", e.toString());
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(String result) {
                            try {
                                progress_wheel.setVisibility(View.GONE);
                                JSONObject jsonObjects = new JSONObject(result);
                                String resultcode = jsonObjects.getString("resultno");
                                if (resultcode.equals("000")) {
                                    String data = result.replace("\\", "").replace("\"[", "[").replace("]\"", "]");
                                    JSONObject jsonObject = new JSONObject(data);
                                    String resultjson = jsonObject.getString("resultjson");

                                    Gson gson = new Gson();
                                    linkmans = gson.fromJson(resultjson, new TypeToken<List<AddressBean>>() {
                                    }.getType());
                                    Message msg = new Message();
                                    msg.what = CodeContacts.SUCCESS_STATE;
                                    msg.obj = linkmans;
                                    handler.sendMessage(msg);
                                } else {
                                    Message msg = new Message();
                                    msg.what = CodeContacts.NODATA_ERROR;
                                    handler.sendMessage(msg);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CodeContacts.SUCCESS_STATE:
                    recyclerView.setLayoutManager(new LinearLayoutManager(EditAddressMainActivity.this));
                    recyclerView.setItemAnimator(new CustomItemAnimator());
                    adapter = new AddressMainAdapter(EditAddressMainActivity.this, (List<AddressBean>) msg.obj);
                    recyclerView.setAdapter(adapter);
                    break;
            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }
}

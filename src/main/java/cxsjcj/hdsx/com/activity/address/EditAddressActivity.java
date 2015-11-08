package cxsjcj.hdsx.com.activity.address;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.konifar.fab_transformation.FabTransformation;
import com.squareup.okhttp.Request;

import cxsjcj.hdsx.com.activity.base.MyBaseActivity;
import cxsjcj.hdsx.com.bean.AddressBean;
import cxsjcj.hdsx.com.bean.CityModel;
import cxsjcj.hdsx.com.bean.CountryModel;
import cxsjcj.hdsx.com.bean.ProvinceModel;
import cxsjcj.hdsx.com.contacts.CodeContacts;
import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.network.okhttp.OkHttpClientManager;
import cxsjcj.hdsx.com.utils.Base64;
import cxsjcj.hdsx.com.utils.Md5Utils;
import cxsjcj.hdsx.com.utils.SingleTrueUtils;
import cxsjcj.hdsx.com.view.CardViewPlus;
import cxsjcj.hdsx.com.view.NiceSpinner;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

import android.support.design.widget.FloatingActionButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
/**
 * @author: 梁铖城
 * @date:2015年9月9日15:09:27
 * @description: 对增加地址的activity
 * @blog: http:www.17yxb.cn
 */
public class EditAddressActivity extends MyBaseActivity implements ViewTreeObserver.OnGlobalLayoutListener {
    private boolean isTransforming;
    private TextInputLayout textInput1;
    private List<ProvinceModel> provinceList; //地址列表
    private int pPosition;
    private int cPosition;
    private boolean isCity = true;
    private boolean isCounty = true;
    private String AddressXML;
    private NiceSpinner ns_spinner2, ns_spinner1, niceSpinner;
    private MaterialDialog materialdialog;
    private EditText et_mainaddress, et_name, et_phone;
    private String totaladdress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textInput1 = (TextInputLayout) findViewById(R.id.textInput1);
        et_mainaddress = (EditText) findViewById(R.id.et_mainaddress);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getColorPrimary());
        toolbar.setTitle("地址管理");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        niceSpinner = (NiceSpinner) findViewById(R.id.ns_spinner);
        ns_spinner2 = (NiceSpinner) findViewById(R.id.ns_spinner2);
        ns_spinner1 = (NiceSpinner) findViewById(R.id.ns_spinner1);
        InitData();
        findViewById(R.id.btn_add_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMaterialDialog();
                getUsers();
            }
        });
        niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                pPosition = position;
                createDialog(2);
                createDialog(3);
            }
        });
        ns_spinner1.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                cPosition = position;
                createDialog(3);
            }
        });
        createDialog(1);
        createDialog(2);
        createDialog(3);
        niceSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(1);
            }
        });
        ns_spinner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(2);
            }
        });
        mContentView = (CardViewPlus) findViewById(R.id.content);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected boolean isopen() {
        return true;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_edit_address;
    }

    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    private void InitData() {
        AddressXML = getRawAddress().toString();//获取中国省市区信息
        try {
            analysisXML(AddressXML);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        //初始化列表下标
        pPosition = 0;
        cPosition = 0;
    }

    public StringBuffer getRawAddress() {
        InputStream in = getResources().openRawResource(R.raw.address);
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(isr);
        StringBuffer sb = new StringBuffer();
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            br.close();
            isr.close();
            in.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return sb;
    }

    public void analysisXML(String data) throws XmlPullParserException {
        try {
            ProvinceModel provinceModel = null;
            CityModel cityModel = null;
            CountryModel countyModel = null;
            List<CityModel> cityList = null;
            List<CountryModel> countyList = null;

            InputStream xmlData = new ByteArrayInputStream(data.getBytes("UTF-8"));
            XmlPullParserFactory factory = null;
            factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser;
            parser = factory.newPullParser();
            parser.setInput(xmlData, "utf-8");
            String currentTag = null;

            String province;
            String city;
            String county;

            int type = parser.getEventType();
            while (type != XmlPullParser.END_DOCUMENT) {
                String typeName = parser.getName();

                if (type == XmlPullParser.START_TAG) {
                    if ("root".equals(typeName)) {
                        provinceList = new ArrayList<ProvinceModel>();

                    } else if ("province".equals(typeName)) {
                        province = parser.getAttributeValue(0);//获取标签里第一个属性,例如<city name="北京市" index="1">中的name属性
                        provinceModel = new ProvinceModel();
                        provinceModel.setProvince(province);
                        cityList = new ArrayList<CityModel>();

                    } else if ("city".equals(typeName)) {
                        city = parser.getAttributeValue(0);
                        cityModel = new CityModel();
                        cityModel.setCity(city);
                        countyList = new ArrayList<CountryModel>();

                    } else if ("area".equals(typeName)) {
                        county = parser.getAttributeValue(0);
                        countyModel = new CountryModel();
                        countyModel.setCounty(county);

                    }
                    currentTag = typeName;

                } else if (type == XmlPullParser.END_TAG) {
                    if ("root".equals(typeName)) {

                    } else if ("province".equals(typeName)) {
                        provinceModel.setCity_list(cityList);
                        provinceList.add(provinceModel);

                    } else if ("city".equals(typeName)) {
                        cityModel.setCounty_list(countyList);
                        cityList.add(cityModel);

                    } else if ("area".equals(typeName)) {
                        countyList.add(countyModel);
                    }
                } else if (type == XmlPullParser.TEXT) {
                    currentTag = null;
                }
                type = parser.next();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDialog(final int type) {
        if (type == 1) {
            ArrayList<Object> p = new ArrayList<>();
            for (int i = 0; i < provinceList.size(); i++) {
                p.add(provinceList.get(i).getProvince());
            }
            niceSpinner.attachDataSource(p);
        } else if (type == 2) {
            ArrayList<Object> p = new ArrayList<>();
            for (int i = 0; i < provinceList.get(pPosition).getCity_list().size(); i++) {
                p.add(provinceList.get(pPosition).getCity_list().get(i).getCity());
            }
            ns_spinner1.attachDataSource(p);
        } else if (type == 3) {
            ArrayList<Object> p = new ArrayList<>();
            for (int i = 0; i < provinceList.get(pPosition).getCity_list().get(cPosition).getCounty_list().size(); i++) {
                p.add(provinceList.get(pPosition).getCity_list().get(cPosition).getCounty_list().get(i).getCounty());
            }
            ns_spinner2.attachDataSource(p);
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    public void onGlobalLayout() {
        if (Build.VERSION.SDK_INT >= 16) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else {
            getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }

        startRevealTransition();
    }

    private CardViewPlus mContentView;

    protected View getRootView() {
        return mContentView;
    }

    protected ViewTreeObserver getViewTreeObserver() {
        return getRootView().getViewTreeObserver();
    }

    protected void startRevealTransition() {
        final Rect bounds = new Rect();
        getRootView().getHitRect(bounds);
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(getRootView(),
                bounds.left, bounds.top, 0, hypo(bounds.height(), bounds.width()));
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    public static float hypo(float a, float b) {
        return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }


    private void showMaterialDialog() {
        materialdialog = new MaterialDialog.Builder(this)
                .content("上传地址信息中，请稍后...")
                .progress(true, 0)
                .progressIndeterminateStyle(false).show();
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
            totaladdress = niceSpinner.getText().toString() + ns_spinner1.getText().toString()
                    + ns_spinner2.getText().toString() + et_mainaddress.getText().toString();
            long time = System.currentTimeMillis();
            String timechou = time + "";
            timechou = timechou.substring(0, timechou.length() - 3);
            Map<String, String> params = new HashMap<>();
            params.put("phone", "18813149871");
            params.put("address", totaladdress);
            params.put("addressee", et_name.getText().toString().trim());
            params.put("aphone", et_phone.getText().toString().trim());
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
            OkHttpClientManager.postAsyn("http://115.28.2.207:8001/Interface/AddressInfo.ashx?action=add&Calldate=" + timechou,
                    new OkHttpClientManager.Param[]{
                            new OkHttpClientManager.Param("CallUser", "18813149871"),
                            new OkHttpClientManager.Param("callValue", jsonRequest.toString()),
                            new OkHttpClientManager.Param("CallSiganture", base)
                    },
                    new MyResultCallback<String>() {
                        @Override
                        public void onError(Request request, Exception e) {
                            Log.e("okhttp", e.toString());
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(String result) {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String resultno = jsonObject.getString("resultno");
                                if (resultno.equals("000")) {
                                    JSONObject object = new JSONObject(jsonObject.getString("resultjson"));
                                    Message message = handler.obtainMessage();
                                    message.what = 100;
                                    handler.sendMessage(message);

                                } else {
                                    Message message = handler.obtainMessage();
                                    message.what = 101;
                                    message.obj = resultno;
                                    handler.sendMessage(message);
                                }

                            } catch (Exception e) {
                                handler.sendMessage(Message.obtain(handler, 102));
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
            if (materialdialog != null && materialdialog.isShowing()) {
                materialdialog.dismiss();
            }
            switch (msg.what) {
                case 100:
                    Snackbar.make(et_mainaddress, "上传成功", Snackbar.LENGTH_LONG)
                      .setAction("Action", null).show();
                    Intent intent=new Intent();
                    setResult(RESULT_OK,intent);
                    finish();
                    break;
                case 101:
                    Snackbar.make(et_mainaddress,"上传失败", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    break;
                case 102:
                    Snackbar.make(et_mainaddress,"上传失败", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    break;
            }
        }
    };
}

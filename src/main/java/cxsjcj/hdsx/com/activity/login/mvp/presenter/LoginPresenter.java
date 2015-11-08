package cxsjcj.hdsx.com.activity.login.mvp.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cxsjcj.hdsx.com.activity.login.mvp.bean.LoginBean;
import cxsjcj.hdsx.com.activity.login.mvp.model.ILoginModel;
import cxsjcj.hdsx.com.activity.login.mvp.view.ILoginView;
import cxsjcj.hdsx.com.activity.login.mvp.impls.LoginModel;
import cxsjcj.hdsx.com.bean.AddressBean;
import cxsjcj.hdsx.com.contacts.CodeContacts;
import cxsjcj.hdsx.com.network.okhttp.OkHttpClientManager;
import cxsjcj.hdsx.com.utils.Base64;
import cxsjcj.hdsx.com.utils.Md5Utils;
import cxsjcj.hdsx.com.utils.SingleTrueUtils;

/**
 * Created by chengcheng on 2015/9/10.
 */
public class LoginPresenter {

    private ILoginView loginView;
    private ILoginModel loginModel;
    private String results;
    private Context context;
    private MaterialDialog materialdialog;

    public LoginPresenter(ILoginView view) {
        loginView = view;
        loginModel = new LoginModel();
    }

    /**
     * 这个是  mvp p层按照正常的道理 这个方法应该写在m里面的
     * @param name     用户名
     * @param password 密码
     * @param context  上下文对象
     */
    public void sendLoginData(String name, String password, Context context) {
        this.context = context;
        showMaterialDialog();
        Map<String, String> map = new HashMap<>();
        map.put("phone", name);
        map.put("pwd", Md5Utils.encode(password).toUpperCase());
        getUsers(map);
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

    /**
     * 请求远程的服务器进行登入操作
     * @param params 传递进来的参数包括用户名和密码
     */
    public void getUsers(Map<String, String> params) {
        try {
            final Message message = new Message();
            long time = System.currentTimeMillis();
            String timechou = time + "";
            timechou = timechou.substring(0, timechou.length() - 3);
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
            OkHttpClientManager.postAsyn("http://115.28.2.207:8001/Interface/Login.ashx?Calldate=" + timechou,
                    new OkHttpClientManager.Param[]{
                            new OkHttpClientManager.Param("CallUser", "18813149871"),
                            new OkHttpClientManager.Param("callValue", jsonRequest.toString()),
                            new OkHttpClientManager.Param("CallSiganture", base)
                    },
                    new MyResultCallback<String>() {
                        @Override
                        public void onError(Request request, Exception e) {
                            message.what = CodeContacts.FAIL_STATE;
                            handler.sendMessage(message);
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(String result) {
                            try {
                                JSONObject jsonObjects = new JSONObject(result);
                                String resultcode = jsonObjects.getString("resultno");
                                if (resultcode.equals("000")) {
                                    message.what = CodeContacts.SUCCESS_STATE;
                                    handler.sendMessage(message);
                                } else {
                                    message.what = CodeContacts.REQUEST_STATE;
                                    handler.sendMessage(message);
                                }
                            } catch (Exception e) {
                                handler.sendEmptyMessage(CodeContacts.JIXIE_STATE);
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (Exception e) {
            results = "2";
            e.printStackTrace();
        }
    }

    public void getLoginData() {
        //从本地读取本地的数据
        LoginBean loginBean = loginModel.load();
        if (loginBean == null) {
            loginView.setName("");
            loginView.setPassword("");
        } else {
            loginView.setName(loginBean.getName());
            loginView.setPassword(loginBean.getPassword());
        }
    }

    private void showMaterialDialog() {
        materialdialog = new MaterialDialog.Builder(context)
                .content("正在登入，请稍后...")
                .progress(true, 0)
                .progressIndeterminateStyle(false).show();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (materialdialog != null && materialdialog.isShowing()) {
                materialdialog.dismiss();
            }

            switch (msg.what) {
                case CodeContacts.SUCCESS_STATE:
                    Toast.makeText(context, "登入成功", Toast.LENGTH_SHORT).show();
                    break;
                case CodeContacts.FAIL_STATE://SHIBAI
                    Toast.makeText(context, "请检查你的网络", Toast.LENGTH_SHORT).show();
                    break;
                case CodeContacts.REQUEST_STATE://bushi 000 密码等错误的综合
                    Toast.makeText(context, "登入失败", Toast.LENGTH_SHORT).show();
                    break;
                case CodeContacts.JIXIE_STATE://bushi 000
                    Toast.makeText(context, "服务错误请联系管理员", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

}

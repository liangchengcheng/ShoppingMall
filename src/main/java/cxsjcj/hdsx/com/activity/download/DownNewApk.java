package cxsjcj.hdsx.com.activity.download;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.utils.SingleTrueUtils;
import is.arontibo.library.ElasticDownloadView;
import is.arontibo.library.ProgressDownloadView;

/**
 * @note: 注意此处可以将
 * @author: 梁铖城
 * @date: 2015年8月27日22:41:51
 * @description: 这个是程序更新的界面，在这个界面你可以检测你的程序是否需要更新
 * @blog: http:www.17yxb.cn
 */
public class DownNewApk extends AppCompatActivity {
    ElasticDownloadView elasticDownloadView;
    public static final String UPDATE_APK_URL = "http://115.28.2.207:8008/hsfshopping.txt";
    public static final String APK_NAME = "gdpad.apk";
    public static final String APK_SAVE_PATH = Environment
            .getExternalStorageDirectory().getPath() + "/com.hdsx.gdpad/";
    private String versionName = "";
    public static final String APKPAKEG_NAME = "com.hdsx.gdpad";
    private static final int DOWNLOAD = 1;
    private static final int DOWNLOAD_FINISH = 2;
    private String mXMLPath;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_new_apk);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getColorPrimary());
        toolbar.setTitle("检测更新");
        toolbar.setTitleTextColor(getColor(R.color.action_bar_title_color));
        toolbar.collapseActionView();
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        elasticDownloadView= (ElasticDownloadView) findViewById(R.id.elastic_download_view);
        elasticDownloadView.setBackgroundColor(getResources().getColor(R.color.btn_send_normal));
        elasticDownloadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upDataAPK();
            }
        });

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
    private void upDataAPK() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String sb = "";
                try {
                    URL url = new URL(UPDATE_APK_URL);
                    URLConnection conn = url.openConnection();
                    InputStreamReader in = new InputStreamReader(conn.getInputStream(),"utf-8");
                    BufferedReader br = new BufferedReader(in);
                    String line = null;
                    while ((line = br.readLine()) != null){
                        sb += line;
                    }
                    in.close();
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(sb)) {
                    Looper.prepare();
                    Toast.makeText(DownNewApk.this, "当前已是最新版本", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                    return;
                }
                try {
                    JSONObject jb = new JSONObject(sb);
                    if (jb.getBoolean("success")) {
                        String info=jb.getString("data");
                        Map<String , String> map= parseJsonObject(info);
                        int versionCode = Integer.parseInt(map.get("code"));
                        if (versionCode > getVersionCode(DownNewApk.this, APKPAKEG_NAME)) {
                            Message message = Message.obtain();
                            message.obj = map.get("url");
                            message.what = 3;
                            mHandler.sendMessage(message);
                        } else {
                            Looper.prepare();
                            Toast.makeText(DownNewApk.this, "已经是最新版本", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    } else {
                       // Frame.getInstance().toastPrompt(jb.getString("error_desc"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static HashMap<String, String> parseJsonObject(String str) throws JSONException{
        HashMap<String, String> hashMap = new HashMap<String, String>();
        JSONObject jb = new JSONObject(str);
        hashMap.put("code", jb.getInt("code")+"");
        hashMap.put("version", jb.getString("version"));
        hashMap.put("url", jb.getString("downloadUrl"));

        return hashMap;
    }
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD:
                    elasticDownloadView.setProgress(msg.arg1);
                    break;
                case DOWNLOAD_FINISH:
                    installApk();
                    break;
                case 3:
                    String url = (String)msg.obj;
                    showNoticeDialog(url);
                    break;
            }
        }
    };
    //显示软件更新对话框
    public void showNoticeDialog(String path){
        mXMLPath = path;
        new MaterialDialog.Builder(this)
                .title("你的APK有更新啦")
                .content("你的APK有更新啦")
                .positiveText("现在下载")
                .negativeText("以后再说")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                        elasticDownloadView.startIntro();
                        showDownloadDialog();
                    }
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        String localtime= SingleTrueUtils.singlechou(System.currentTimeMillis());
                        sp.edit().putString("updatetime",localtime).commit();
                        dialog.dismiss();
                    }
                })
                .show();
    }
    /**
     * 显示下载对话框
     */
    private void showDownloadDialog(){
//        mDialog = new CustomHorizontalProgressDialog(this);
//        mDialog.setCancelable(false);
//        mDialog.show();
        //下载文件
        downloadAPK();
    }
    /**
     * 下载apk文件
     */
    private void downloadAPK() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                //判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    //获取sd卡存储的路径
                    File tmpFile = new File(APK_SAVE_PATH);
                    if (!tmpFile.exists()) {
                        tmpFile.mkdir();
                    }
                    URL url;
                    try {
                        url = new URL(mXMLPath);
                        // 创建连接
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.connect();
                        // 获取文件大小
                        int length = conn.getContentLength();
                        // 创建输入流
                        InputStream is = conn.getInputStream();
                        File apkFile = new File(APK_SAVE_PATH, APK_NAME);
                        FileOutputStream fos = new FileOutputStream(apkFile);
                        int count = 0;
                        // 缓存
                        byte buf[] = new byte[1024];
                        // 写入到文件中
                        int numread;
                        while ((numread = is.read(buf)) > 0) {
                            count += numread;
                            // 计算进度条位置
                            Message msg = Message.obtain();
                            msg.arg1 = (int) (((float) count / length) * 100);
                            msg.what = DOWNLOAD;
                            // 更新进度
                            mHandler.sendMessage(msg);
                            // 写入文件
                            fos.write(buf, 0, numread);
                        }

                        if (numread <= 0) {
                            // 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                        }
                        fos.close();
                        is.close();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //mDialog.dismiss();
                }
            }
        }).start();
    }

    /**
     * 安装APK文件
     */
    private void installApk() {
        File apkfile = new File(APK_SAVE_PATH + APK_NAME);
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setAction(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
        startActivity(i);
    }
    public static int getVersionCode(Context ctx, String packageName) {
        if (null == packageName || packageName.length() <= 0) {
            return -1;
        }
        try {
            PackageInfo info = ctx.getPackageManager().getPackageInfo(packageName, 0);
            return info.versionCode;
        } catch (Exception e) {
            return -1;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

package cxsjcj.hdsx.com.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
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


/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:   2015年10月16日14:01:47
 * Description: 对软件更新的封装
 */

public class UpDateApkUtils {
    //远程服务器的更新地址
    public static final String UPDATE_APK_URL = "http://115.28.2.207:8008/hsfshopping.txt";
    //给下载的apk起一个名字
    public static final String APK_NAME = "gdpad.apk";
    //现在的apk的保存的在sd卡的地址
    public static final String APK_SAVE_PATH = Environment
            .getExternalStorageDirectory().getPath() + "/com.hdsx.gdpad/";
    //本地apk的名字这里是为了测试，请注意替换为你本程序的包名
    public static final String APKPAKEG_NAME = "com.hdsx.gdpad";
    //下面2个是下载的标记
    private static final int DOWNLOAD = 1;
    private static final int DOWNLOAD_FINISH = 2;
    private String mXMLPath;
    SharedPreferences sp;
    private Context context;
    private Thread mThread;
    private MaterialDialog dialog;

    public UpDateApkUtils(Context context) {
        this.context = context;
        sp=context.getSharedPreferences("lcc",Context.MODE_PRIVATE);
    }

    public void upDataAPK() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String sb = "";
                try {
                    URL url = new URL(UPDATE_APK_URL);
                    URLConnection conn = url.openConnection();
                    InputStreamReader in = new InputStreamReader(conn.getInputStream(), "utf-8");
                    BufferedReader br = new BufferedReader(in);
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb += line;
                    }
                    in.close();
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(sb)) {
                    Looper.prepare();
                    Toast.makeText(context, "当前已是最新版本", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                    return;
                }
                try {
                    JSONObject jb = new JSONObject(sb);
                    if (jb.getBoolean("success")) {
                        String info = jb.getString("data");
                        Map<String, String> map = parseJsonObject(info);
                        int versionCode = Integer.parseInt(map.get("code"));
                        if (versionCode > getVersionCode(context, APKPAKEG_NAME)) {
                            Message message = Message.obtain();
                            message.obj = map.get("url");
                            message.what = 3;
                            mHandler.sendMessage(message);
                        } else {
                            Looper.prepare();
                            Toast.makeText(context, "已经是最新版本", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    } else {
                        Looper.prepare();
                        Toast.makeText(context, "下载失败，请稍后再试", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static HashMap<String, String> parseJsonObject(String str) throws JSONException {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        JSONObject jb = new JSONObject(str);
        hashMap.put("code", jb.getInt("code") + "");
        hashMap.put("version", jb.getString("version"));
        hashMap.put("url", jb.getString("downloadUrl"));
        return hashMap;
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD:
                    dialog.setProgress(msg.arg1);
                    break;
                case DOWNLOAD_FINISH:
                    installApk();
                    break;
                case 3:
                    String url = (String) msg.obj;
                    showNoticeDialog(url);
                    break;
            }
        }
    };

    //显示软件更新对话框
    public void showNoticeDialog(String path) {
        mXMLPath = path;
        new MaterialDialog.Builder(context)
                .title("你的APK有更新啦")
                .content("你的APK有更新啦")
                .positiveText("现在下载")
                .negativeText("以后再说")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                        showProgressDialog(false, false);
                        downloadAPK();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        String localtime = SingleTrueUtils.singlechou(System.currentTimeMillis());
                        sp.edit().putString("updatetime", localtime).commit();
                        dialog.dismiss();
                    }
                })
                .show();
    }

    //下载远程服务器上面的apk文件
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
                }
            }
        }).start();
    }

    //安装下载好的apk文件
    private void installApk() {
        File apkfile = new File(APK_SAVE_PATH + APK_NAME);
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setAction(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
        context.startActivity(i);
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

    private void showProgressDialog(boolean indeterminate, boolean horizontalIndeterminate) {
        new MaterialDialog.Builder(context)
                .title("APP更新")
                .content("正在为你下载最新版的app请稍后...")
                .contentGravity(GravityEnum.CENTER)
                .progress(false, 100, true)
                .cancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (mThread != null)
                            mThread.interrupt();
                    }
                })
                .showListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        dialog = (MaterialDialog) dialogInterface;

                        startThread(new Runnable() {
                            @Override
                            public void run() {
                                if (dialog.getCurrentProgress() == dialog.getMaxProgress()) {
                                    dialog.dismiss();
                                }
                            }
                        });
                    }
                }).show();

    }

    private void startThread(Runnable run) {
        if (mThread != null)
            mThread.interrupt();
        mThread = new Thread(run);
        mThread.start();
    }

}

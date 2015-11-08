package cxsjcj.hdsx.com.activity.login;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import cxsjcj.hdsx.com.activity.login.mvp.presenter.LoginPresenter;
import cxsjcj.hdsx.com.activity.login.mvp.view.ILoginView;
import cxsjcj.hdsx.com.activity.region.RegionActivity;
import cxsjcj.hdsx.com.activity.setting.SysTemSettingActivity;
import cxsjcj.hdsx.com.myapplication.R;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年10月14日11:17:52
 * Description:  登入采用的MVP架构设计的
 */

// TODO: 2015/10/14 进度：80%，格式：不合格，注意：需要将特殊界面去掉 ，未完成模块：无
public class LoginActivity extends AppCompatActivity implements ILoginView {
    private EditText username;
    private EditText et_password;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.name);
        et_password = (EditText) findViewById(R.id.password);
        findViewById(R.id.land).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.sendLoginData(getName(), getPassword(), LoginActivity.this);
            }
        });
        findViewById(R.id.buton_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        findViewById(R.id.button_weibo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SysTemSettingActivity.class));
            }
        });
        findViewById(R.id.imageView1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegionActivity.class));
            }
        });
        findViewById(R.id.forget_pass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,MDLogin.class));
            }
        });
        final TextInputLayout inputLayout = (TextInputLayout) findViewById(R.id.textInput);
        inputLayout.setHint("请输入姓名:");

        EditText editText = inputLayout.getEditText();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 14) {
                    inputLayout.setErrorEnabled(true);
                    inputLayout.setError("姓名长度不能超过14个");
                } else {
                    inputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final TextInputLayout inputLayout1 = (TextInputLayout) findViewById(R.id.textInput1);
        inputLayout1.setHint("请输入密码");

        EditText editText1 = inputLayout1.getEditText();
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 6) {
                    inputLayout1.setErrorEnabled(true);
                    inputLayout1.setError("密码长度大于6");
                } else {
                    inputLayout1.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        presenter = new LoginPresenter(this);
//      TextInputLayout 不仅能让EditView的提示语上弹显示在EditView之上，而且还能把错误信息显示在EditView之下。TextInputLayout常用的方法有如下：
//      [*]setHint()：设置提示语。
//      [*]getEditText()：得到TextInputLayout中的EditView控件。
//      [*]setErrorEnabled():设置是否可以显示错误信息。
//      [*]setError()：设置当用户输入错误时弹出的错误信息。
    }

    @Override
    public String getName() {
        return username.getText().toString();
    }

    @Override
    public String getPassword() {
        return et_password.getText().toString();
    }

    @Override
    public void setName(String name) {
        username.setText(name);
    }

    @Override
    public void setPassword(String password) {
        et_password.setText(password);
    }
}

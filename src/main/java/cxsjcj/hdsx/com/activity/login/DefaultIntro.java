package cxsjcj.hdsx.com.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.github.paolorotolo.appintro.AppIntro;

import cxsjcj.hdsx.com.activity.main.MainActivity;
import cxsjcj.hdsx.com.myapplication.R;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年10月14日11:17:10
 * Description:  引导界面的总的界面
 */

// TODO: 2015/10/14 进度：100%，格式：合格，注意：无 ，未完成模块：无
public class DefaultIntro extends AppIntro {
    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(new FirstSlide(), getApplicationContext());
        addSlide(new SecondSlide(), getApplicationContext());
        addSlide(new ThirdSlide(), getApplicationContext());
        addSlide(new FourthSlide(), getApplicationContext());
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSkipPressed() {
        loadMainActivity();
        Toast.makeText(getApplicationContext(),getString(R.string.skip),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDonePressed() {
        loadMainActivity();
    }

    public void getStarted(View v){
        loadMainActivity();
    }
}

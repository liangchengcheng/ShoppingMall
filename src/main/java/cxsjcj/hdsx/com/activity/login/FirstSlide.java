package cxsjcj.hdsx.com.activity.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cxsjcj.hdsx.com.myapplication.R;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com 
 * Date:  2015年10月14日11:10:51  
 * Description:     引导界面第一个
 */

// TODO: 2015/10/14 进度：100%，格式：合格，注意：无 ，未完成模块：无  
public class FirstSlide extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.intro, container, false);
        return v;
    }
}

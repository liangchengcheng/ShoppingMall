package cxsjcj.hdsx.com.activity.userinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cxsjcj.hdsx.com.myapplication.R;

/**
 * @author: 梁铖城
 * @date: 2015年9月9日08:50:00
 * @description: 个人简介的界面
 * @blog: http:www.17yxb.cn
 */
public class UserDataFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user_data,null);
        return view;
    }

}

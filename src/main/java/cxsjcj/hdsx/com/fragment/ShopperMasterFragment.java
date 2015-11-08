package cxsjcj.hdsx.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

import cxsjcj.hdsx.com.activity.detail.ShoppingDoneActivity;
import cxsjcj.hdsx.com.adapter.HomeTestGridAdapter;
import cxsjcj.hdsx.com.itemanimation.FadeInAnimator;
import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.utils.FullyGridLayoutManager;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年10月25日20:54:15
 * Description:  店主推荐的界面
 */
public class ShopperMasterFragment extends Fragment {

    private  RecyclerView recyclerView;
    private static String[] data = new String[]{
            "泉哥的内裤", "泉哥的胸罩", "泉哥的娃娃", "泉哥的纸巾"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.shopper_master_fragment, null);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.list);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FullyGridLayoutManager manager=new FullyGridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new FadeInAnimator());
        HomeTestGridAdapter adapter = new HomeTestGridAdapter(getActivity(),
                new ArrayList<>(Arrays.asList(data)));
        recyclerView.setAdapter(adapter);
    }
}

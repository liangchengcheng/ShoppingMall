package cxsjcj.hdsx.com.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import cxsjcj.hdsx.com.myapplication.R;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:  2015年10月28日10:25:51
 * Description:  关于产品生产规格详情的简单的介绍
 */

public class ProductParameterFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_parameter_fragment, null);
        ListView list= (ListView) rootView.findViewById(R.id.list);
        ArrayList<HashMap<String,Object>> gridList = new ArrayList<HashMap<String, Object>>();
        HashMap<String,Object> item = new HashMap<String,Object>();
        item.put("name","生产厂家");
        item.put("value", "北京市海淀区上地街道");
        gridList.add(item);

        item = new HashMap<String,Object>();
        item.put("name","生产厂家");
        item.put("value", "北京市海淀区上地街道");
        gridList.add(item);

        item = new HashMap<String,Object>();
        item.put("name","生产厂家");
        item.put("value", "北京市海淀区上地街道");
        gridList.add(item);

        item = new HashMap<String,Object>();
        item.put("name","生产厂家");
        item.put("value", "北京市海淀区上地街道");
        gridList.add(item);

        item = new HashMap<String,Object>();
        item.put("name","生产厂家");
        item.put("value", "北京市海淀区上地街道");
        gridList.add(item);

        item = new HashMap<String,Object>();
        item.put("name","生产厂家");
        item.put("value", "北京市海淀区上地街道");
        gridList.add(item);

        item = new HashMap<String,Object>();
        item.put("name","生产厂家");
        item.put("value", "北京市海淀区上地街道");
        gridList.add(item);

        SimpleAdapter gridAdapter = new SimpleAdapter(getActivity(), gridList, R.layout.product_parameter_item_layout,
                new String[]{"name","value"}, new int[]{R.id.tv_name,R.id.tv_value});
        list.setAdapter(gridAdapter);
        return rootView;
    }
}

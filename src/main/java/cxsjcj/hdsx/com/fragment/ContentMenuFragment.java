package cxsjcj.hdsx.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cxsjcj.hdsx.com.adapter.MasonryAdapter;
import cxsjcj.hdsx.com.bean.Product;
import cxsjcj.hdsx.com.ipl.SpacesItemDecoration;
import cxsjcj.hdsx.com.myapplication.R;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date: 2015年11月1日14:45:11
 * Description: 第二个主页面主要显示菜单的
 */
public class ContentMenuFragment  extends Fragment{
    RecyclerView recyclerView;
    List<Product> productList=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_menu_layout, null);
        recyclerView= (RecyclerView) view.findViewById(R.id.list);
        //设置layoutManager
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        //设置adapter
        initData();
        MasonryAdapter adapter=new MasonryAdapter(productList);
        recyclerView.setAdapter(adapter);
        //设置item之间的间隔
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);
        return view;
    }

    private void initData(){
        Product product=new Product(R.drawable.q1,"华为MATE");
        productList.add(product);
        Product product1=new Product(R.drawable.q2,"小美专卖");
        productList.add(product1);
        Product product2=new Product(R.drawable.q3,"MACPRO");
        productList.add(product2);
        Product product3=new Product(R.drawable.q4,"华为太极本");
        productList.add(product3);
        Product product4=new Product(R.drawable.q5,"华为P9");
        productList.add(product4);
        Product product5=new Product(R.drawable.q6,"酷派1001s");
        productList.add(product5);
        Product product6=new Product(R.drawable.q7,"VIVO1107t");
        productList.add(product6);
        Product product7=new Product(R.drawable.q1,"小米5s");
        productList.add(product7);
        Product product8=new Product(R.drawable.q4,"金立M5");
        productList.add(product8);

    }
}

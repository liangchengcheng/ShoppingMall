package cxsjcj.hdsx.com.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rey.material.widget.SnackBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cxsjcj.hdsx.com.activity.address.TransformToPlayerActivity;
import cxsjcj.hdsx.com.activity.address.TransformToSheetActivity;
import cxsjcj.hdsx.com.activity.circle.MainCircleActivity;
import cxsjcj.hdsx.com.activity.detail.ShoppingListActivity;
import cxsjcj.hdsx.com.activity.detail.ShoppingMainActivity;
import cxsjcj.hdsx.com.activity.download.DownNewApk;
import cxsjcj.hdsx.com.activity.main.MainJBActivityActivity;
import cxsjcj.hdsx.com.activity.main.MainZBActivity;
import cxsjcj.hdsx.com.activity.news.NewsDeailsActivity;
import cxsjcj.hdsx.com.activity.news.NewsMainActivity;
import cxsjcj.hdsx.com.adapter.AnimAdapter;
import cxsjcj.hdsx.com.adapter.GalleryAdapter;
import cxsjcj.hdsx.com.adapter.HomeGridAdapter;
import cxsjcj.hdsx.com.bean.Product;
import cxsjcj.hdsx.com.itemanimation.Advertisements;
import cxsjcj.hdsx.com.itemanimation.AlphaInAnimationAdapter;
import cxsjcj.hdsx.com.itemanimation.FadeInAnimator;
import cxsjcj.hdsx.com.itemanimation.ScaleInAnimationAdapter;
import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.network.RequestManager;
import cxsjcj.hdsx.com.test.listanimation.MainAdapter;
import cxsjcj.hdsx.com.view.FullyGridLayoutManager;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年10月25日15:50:25
 * Description:  主界面当中的第一个界面
 */
public class HomeFragment extends Fragment {
    private LinearLayout llAdvertiseBoard;
    private LayoutInflater inflaters;
    ArrayList<Product> productList=new ArrayList<>();
    private static String[] data = new String[]{
            "My JbPage", "My ZbPage", "My NewPage", "My TopNews"
    };


    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViews;
    private GalleryAdapter mAdapter;
    private List<Product> mDatas=new ArrayList<Product>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container, false);
        RequestManager.init(getActivity());
        inflaters = LayoutInflater.from(getActivity());
        llAdvertiseBoard = (LinearLayout) view.findViewById(R.id.llAdvertiseBoard);
        initViews();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new FullyGridLayoutManager(getActivity(), 2));
        recyclerView.setItemAnimator(new FadeInAnimator());
        HomeGridAdapter adapter = new HomeGridAdapter(getActivity());

        adapter.setOnItemClickListener(new HomeGridAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                if (data.equals("My JbPage")) {
                    Snackbar.make(view, data, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    startActivity(new Intent(getActivity(), MainJBActivityActivity.class));

                }
                if (data.equals("My ZbPage")) {
                    Snackbar.make(view, data, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    startActivity(new Intent(getActivity(), MainZBActivity.class));
                }
                if (data.equals("My NewPage")) {
                    Snackbar.make(view, data, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    startActivity(new Intent(getActivity(), DownNewApk.class));
                }
                if (data.equals("My TopNews")) {
                    Snackbar.make(view, data, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    startActivity(new Intent(getActivity(), NewsDeailsActivity.class));
                    //startActivity(new Intent(getActivity(), TransformToPlayerActivity.class));
                    // startActivity(new Intent(getActivity(), TransformToSheetActivity.class));
                }
            }
        });
        //scaleAdapter.setFirstOnly(false);
        //scaleAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(adapter);


        Product product=new Product(R.drawable.bb,"My JbPage");
        productList.add(product);
        Product product1=new Product(R.drawable.cc,"My ZbPage");
        productList.add(product1);
        Product product2=new Product(R.drawable.bb,"My NewPage");
        productList.add(product2);
        Product product3=new Product(R.drawable.ee,"My TopNews");
        productList.add(product3);

        adapter.addTitles(productList);


        initDatas();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview_horizontal);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        mAdapter = new GalleryAdapter(getActivity(), mDatas);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerViews = (RecyclerView) view.findViewById(R.id.id_recyclerview_horizontals);
        LinearLayoutManager linearLayoutManagers = new LinearLayoutManager(getActivity());
        linearLayoutManagers.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerViews.setLayoutManager(linearLayoutManagers);
        //设置适配器
        mRecyclerViews.setAdapter(mAdapter);
        view.findViewById(R.id.iv_mrth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ShoppingListActivity.class));
            }
        });
        return view;
    }

    private void initViews() {

        JSONArray advertiseArray = new JSONArray();
        try {
            JSONObject head_img0 = new JSONObject();
            head_img0.put("head_img", "http://www.17yxb.cn/image/m1.png");
            JSONObject head_img1 = new JSONObject();
            head_img1.put("head_img", "http://www.17yxb.cn/image/m2.png");
            JSONObject head_img2 = new JSONObject();
            head_img2.put("head_img", "http://www.17yxb.cn/image/m3.png");
            JSONObject head_img3 = new JSONObject();
            head_img3.put("head_img", "http://www.17yxb.cn/image/m4.png");
            advertiseArray.put(head_img0);
            advertiseArray.put(head_img1);
            advertiseArray.put(head_img2);
            advertiseArray.put(head_img3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        llAdvertiseBoard.addView(new Advertisements(getActivity(), true, inflaters, 3000).initView(advertiseArray));
    }

    private void initDatas() {

        Product product=new Product(R.drawable.q7,"丝袜");
        mDatas.add(product);
        Product product1=new Product(R.drawable.q6,"打底裤");
        mDatas.add(product1);
        Product product2=new Product(R.drawable.q5,"连衣裙");
        mDatas.add(product2);
        Product product3=new Product(R.drawable.q4,"毛衣");
        mDatas.add(product3);
        Product product4=new Product(R.drawable.q3,"针织衫");
        mDatas.add(product4);
        Product product5=new Product(R.drawable.q2,"牛仔裤");
        mDatas.add(product5);
        Product product6=new Product(R.drawable.q1,"衬衫");
        mDatas.add(product6);
    }

    private void initData(){
        Product product=new Product(R.drawable.bb,"My JbPage");
        productList.add(product);
        Product product1=new Product(R.drawable.cc,"My ZbPage");
        productList.add(product1);
        Product product2=new Product(R.drawable.dd,"My NewPage");
        productList.add(product2);
        Product product3=new Product(R.drawable.ee,"My TopNews");
        productList.add(product3);

    }


}

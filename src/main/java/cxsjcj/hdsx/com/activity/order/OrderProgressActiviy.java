package cxsjcj.hdsx.com.activity.order;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import cxsjcj.hdsx.com.activity.base.MyBaseActivity;
import cxsjcj.hdsx.com.adapter.RouteItemAdapter;
import cxsjcj.hdsx.com.bean.RouteInfo;
import cxsjcj.hdsx.com.fragment.Cheeses;
import cxsjcj.hdsx.com.myapplication.R;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:  2015年10月15日23:40:38
 * Description:   订单的物流进度
 */
public class OrderProgressActiviy extends MyBaseActivity {

    private TextView titleText = null;// 标题
    private ListView routeList = null;// 商品列表
    private List<RouteInfo> infos = new ArrayList<RouteInfo>();
    private ImageView mImageView;
    private ImageView mImageView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageView = (ImageView) findViewById(R.id.avatar);
        mImageView1 = (ImageView) findViewById(R.id.avatar1);
        loadtwoImage();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getColorPrimary());
        setSupportActionBar(toolbar);
        toolbar.setTitle("订单进度");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void loadtwoImage(){
        loadImage(mImageView,R.drawable.shunfeng);
        loadImage(mImageView1,Cheeses.getRandomCheeseDrawable());
    }
    private void loadImage(ImageView imageView,int drawableid){
        Glide.with(imageView.getContext())
                .load(drawableid)
                .fitCenter()
                .into(imageView);
    }
    @Override
    protected boolean isopen() {
        return true;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_order_progress_activiy;
    }

    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }


    private void initView() {
        routeList = (ListView) findViewById(R.id.lv_route_list);

        infos.add(new  RouteInfo("感谢您使用小城商城模板，你的提现已经提交成功", "2014-06-19 10:39:17"));
        infos.add(new  RouteInfo("你的提现正在审核中", "2014-06-19 10:14:17"));
        infos.add(new  RouteInfo("你的提现审核通过，正在打款中预计2小时内到账", "2014-06-19 09:34:38"));
        infos.add(new RouteInfo("你的提现打款已经完成，请查询你的余额", "2014-06-19 07:34:38"));

        RouteItemAdapter itemAdapter = new RouteItemAdapter(
                OrderProgressActiviy.this, infos);
        routeList.setAdapter(itemAdapter);

    }

}

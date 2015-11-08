package cxsjcj.hdsx.com.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import cxsjcj.hdsx.com.myapplication.R;

/**
 * @author: 梁铖城
 * @date: 2015年9月4日23:25:06
 * @description: 删除和编辑的界面
 * @blog: http:www.17yxb.cn
 */
public class DeleteAndEditPopWindowView  extends PopupWindow {
   private  View mMenuView;
    public DeleteAndEditPopWindowView(Context activity,View.OnClickListener listener)
    {
        super(activity);
        LayoutInflater inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView=inflater.inflate(R.layout.pop_view, null);

//        button=(Button)mMenuView.findViewById(R.id.btn_take_photo);
//        button2=(Button)mMenuView.findViewById(R.id.btn_choose_photo);
//        button3=(Button)mMenuView.findViewById(R.id.btn_cancel);
//
//        button3.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                dismiss();//直接摧毁那个popwindow
//
//            }
//        });
//        button.setOnClickListener(listener);
//        button2.setOnClickListener(listener);
        this.setContentView(mMenuView);
        this.setWidth(200);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.popupAnimation);
        ColorDrawable dw=new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);

    }
}


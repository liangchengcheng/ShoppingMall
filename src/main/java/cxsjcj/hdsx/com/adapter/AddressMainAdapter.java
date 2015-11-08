package cxsjcj.hdsx.com.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cxsjcj.hdsx.com.bean.AddressBean;
import cxsjcj.hdsx.com.bean.Zhuangbei;
import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.view.DeleteAndEditPopWindowView;

/**
 * @author  梁铖城
 * @date 2015年8月22日18:20:41
 * @description 地址的主要界面
 */
public class AddressMainAdapter extends RecyclerView.Adapter<AddressMainAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<AddressBean> mDataSet;
    private DeleteAndEditPopWindowView pop_view;

    public AddressMainAdapter(Context context, List<AddressBean> dataSet) {
        mContext = context;
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.activity_shopping_address_item, parent, false);
        v.setOnClickListener(this);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(mDataSet.get(position).getAddressee());
        holder.phone.setText(mDataSet.get(position).getPhone());
        holder.address.setText(mDataSet.get(position).getAddress());
        holder.item_view.setTag(mDataSet.get(position));
        holder.ad_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop_view = new DeleteAndEditPopWindowView(mContext, itemonclick);
//                pop_view.showAtLocation(holder.ad_edit,
//                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);


                pop_view.showAsDropDown(holder.ad_edit);
            }
        });
    }
    private View.OnClickListener itemonclick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
//            p.dismiss();
//            switch (v.getId()) {
//                case R.id.btn_take_photo:
//                    // 在这里其实要先判断下sd卡是否存在。
//                    Intent intent = new Intent(
//                            android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//
//                    // 这个是我参考最新的方法实现的打开相机的
//
//                    if (hasSdcard()) {
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                                Uri.fromFile(new File(IMAGE_FILE_NAME)));
//                    }
//
//                    startActivityForResult(intent, 100);
//                    // 打开拍照的功能
//                    break;
//                case R.id.btn_choose_photo:
//                    openAlbum();
//                    break;
//                case R.id.btn_cancel:
//                    // 这里是文件上传的我暂时放在这
//                    Htpps.sendFromByPost(upload_path, image_data, "aa.png");
//                    break;
//                default:
//                    break;
//            }

        }
    };
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void remove(int position) {
        mDataSet.remove(position);
        notifyItemRemoved(position);
    }

    public void add(ArrayList<AddressBean> text) {
        mDataSet.addAll(text);
        notifyDataSetChanged();
    }
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (Zhuangbei) v.getTag());
        }
    }
    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView selected;
        private TextView name;
        private TextView phone;
        private TextView address;
        private FrameLayout ad_layout;
        private FrameLayout ad_edit;

        public View item_view;

        public ViewHolder(View itemView) {
            super(itemView);
            item_view=itemView;
            selected = (ImageView) itemView.findViewById(R.id.iv_ad_selected);
            name = (TextView) itemView.findViewById(R.id.tv_ad_linkman);
            phone = (TextView) itemView.findViewById(R.id.tv_ad_phone);
            address = (TextView) itemView.findViewById(R.id.tv_address);
            ad_edit = (FrameLayout) itemView.findViewById(R.id.fl_ad_edit);

        }
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Zhuangbei data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}

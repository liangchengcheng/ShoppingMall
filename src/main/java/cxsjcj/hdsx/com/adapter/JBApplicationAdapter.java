package cxsjcj.hdsx.com.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cxsjcj.hdsx.com.activity.main.MainJBActivityActivity;
import cxsjcj.hdsx.com.bean.Jinbi;
import cxsjcj.hdsx.com.myapplication.R;

public class JBApplicationAdapter extends RecyclerView.Adapter<JBApplicationAdapter.ViewHolder> {

    private List<Jinbi> applications;
    private int rowLayout;
    private MainJBActivityActivity mAct;
    OnItemClickListener mItemClickListener;
    public JBApplicationAdapter(List<Jinbi> applications, int rowLayout, MainJBActivityActivity act) {
        this.applications = applications;
        this.rowLayout = rowLayout;
        this.mAct = act;
    }
    public void clearApplications() {
        int size = this.applications.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                applications.remove(0);
            }
            this.notifyItemRangeRemoved(0, size);
        }
    }

    public void addApplications(List<Jinbi> applications) {
        this.applications.addAll(applications);
        this.notifyItemRangeInserted(0, applications.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Jinbi appInfo = applications.get(i);
        viewHolder.name.setText("山东二区"+appInfo.getTitle());
        viewHolder.image.setImageResource(R.drawable.cheese_1);
        viewHolder.jiage.setText("售价："+appInfo.getId());
    }

    @Override
    public int getItemCount() {
        return applications == null ? 0 : applications.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public ImageView image;
        public TextView jiage;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.countryName);
            jiage = (TextView) itemView.findViewById(R.id.countryCount);
            image = (ImageView) itemView.findViewById(R.id.countryImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getLayoutPosition());
            }
        }
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}

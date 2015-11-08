package cxsjcj.hdsx.com.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cxsjcj.hdsx.com.activity.circle.CircularRevealView;
import cxsjcj.hdsx.com.activity.detail.CheeseDetailActivity;
import cxsjcj.hdsx.com.activity.shioce.LetterMainActivity;
import cxsjcj.hdsx.com.myapplication.R;

public class CheeseListFragment extends Fragment implements DialogInterface.OnDismissListener {
    private TextView button;
    private CircularRevealView revealView;
    private View selectedView;
    android.os.Handler handler;
    RelativeLayout layout;
    private int backgroundColor;
    private RecyclerView rv;
    private CardView cd_view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.no_setting_activity, null);
        rv = (RecyclerView) view.findViewById(R.id.recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        cd_view = (CardView) view.findViewById(R.id.cd_view);
        button = (TextView) view.findViewById(R.id.button);
        layout = (RelativeLayout) view.findViewById(R.id.layout);
        backgroundColor = Color.parseColor("#11303030");
        revealView = (CircularRevealView) view.findViewById(R.id.reveal);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int color = getColorPrimary();//Color.parseColor("#00bcd4");
                final Point p = getLocationInView(revealView, v);

                revealView.reveal(p.x, p.y, color, v.getHeight() / 2, 440, null);
                selectedView = v;

                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getActivity(), LetterMainActivity.class);
                        startActivityForResult(intent, 500);
                    }
                }, 235);
            }
        });
        return view;
    }
    public int getStatusBarColor(){
        return getColorPrimary();
    }
    //获取当前的系统颜色
    public int getColorPrimary(){
        TypedValue typedValue = new  TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
    private Point getLocationInView(View src, View target) {
        final int[] l0 = new int[2];
        src.getLocationOnScreen(l0);

        final int[] l1 = new int[2];
        target.getLocationOnScreen(l1);

        l1[0] = l1[0] - l0[0] + target.getWidth() / 2;
        l1[1] = l1[1] - l0[1] + target.getHeight() / 2;

        return new Point(l1[0], l1[1]);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(),
                getRandomSublist(Cheeses.sCheeseStrings, 30)));
    }

    private List<String> getRandomSublist(String[] array, int amount) {
        ArrayList<String> list = new ArrayList<>(amount);
        Random random = new Random();
        while (list.size() < amount) {
            list.add(array[random.nextInt(array.length)]);
        }
        return list;
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<String> mValues;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;

            public final View mView;
            public final ImageView mImageView;
            public final TextView mTextView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.avatar);
                mTextView = (TextView) view.findViewById(android.R.id.text1);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public String getValueAt(int position) {
            return mValues.get(position);
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<String> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mBoundString = mValues.get(position);
            holder.mTextView.setText(mValues.get(position));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, CheeseDetailActivity.class);
                    intent.putExtra(CheeseDetailActivity.EXTRA_NAME, holder.mBoundString);

                    context.startActivity(intent);
                }
            });

            Glide.with(holder.mImageView.getContext())
                    .load(Cheeses.getRandomCheeseDrawable())
                    .fitCenter()
                    .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        View v = button;
        final Point p = getLocationInView(revealView, v);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                revealView.hide(p.x, p.y, backgroundColor, 0, 330, null);
            }
        }, 300);


        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                layout.setVisibility(View.VISIBLE);
            }
        }, 500);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 500) {
            View v = button;
            final Point p = getLocationInView(revealView, v);

            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    revealView.hide(p.x, p.y, backgroundColor, 0, 330, null);
                }
            }, 300);


            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    layout.setVisibility(View.VISIBLE);
                }
            }, 500);
            layout.setVisibility(View.GONE);
            cd_view.setVisibility(View.GONE);
            rv.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(),
                    getRandomSublist(Cheeses.sCheeseStrings, 30)));
        }
    }
}

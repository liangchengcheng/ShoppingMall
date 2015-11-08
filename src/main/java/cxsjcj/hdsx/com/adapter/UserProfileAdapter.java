package cxsjcj.hdsx.com.adapter;

import android.content.Context;
import android.view.animation.Interpolator;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import java.util.Arrays;
import java.util.List;

import cxsjcj.hdsx.com.activity.information.Utils;
import cxsjcj.hdsx.com.myapplication.R;


/**
 * @author  个人中心的装载器
 * @date 2015年8月22日18:21:42
 * @description 个人中心的数据装载器
 */
public class UserProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int PHOTO_ANIMATION_DELAY = 600;
    //这句是干嘛用的我还真的不知道
    private static  final Interpolator INTERPOLATOR = new DecelerateInterpolator();
    private final Context context;
    private final int cellSize;

    private final List<String> photos;

    private boolean lockedAnimations = false;
    private int lastAnimatedItem = -1;
    public  UserProfileAdapter(Context context)
    {
        this.context = context;
        this.cellSize = Utils.getScreenWidth(context) / 3;
        this.photos = Arrays.asList(context.getResources().getStringArray(R.array.user_photos));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

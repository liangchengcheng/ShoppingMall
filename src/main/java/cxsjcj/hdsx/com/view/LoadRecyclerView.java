package cxsjcj.hdsx.com.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.pnikosis.materialishprogress.ProgressWheel;

import cxsjcj.hdsx.com.dao.RecyclerViewPositionHelper;
import cxsjcj.hdsx.com.dao.ScrollDirection;
import cxsjcj.hdsx.com.ipl.PullCallback;
import cxsjcj.hdsx.com.myapplication.R;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:  2015年10月25日14:59:57
 * Description: 对加载更多和刷新的简单的封装
 */
public class LoadRecyclerView extends FrameLayout {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ProgressWheel mProgressBar;
    private PullCallback mPullCallback;
    private RecyclerViewPositionHelper mRecyclerViewHelper;
    private ScrollDirection mCurScrollingDirection;
    protected int mPrevFirstVisibleItem = 0;
    private int mLoadMoreOffset = 5;
    private boolean mIsLoadMoreEnabled = false;

    public LoadRecyclerView(Context context) {
        this(context, null);
    }

    public LoadRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.recycler_load_view, this, true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mProgressBar = (ProgressWheel) findViewById(R.id.progressBar);
        init();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(mRecyclerView);
    }


    private void init() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (null != mPullCallback) {
                    mPullCallback.onRefresh();
                }
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mCurScrollingDirection = null;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mCurScrollingDirection == null) { //User has just started a scrolling motion
                    mCurScrollingDirection = ScrollDirection.SAME;
                    mPrevFirstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
                } else {
                    final int firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
                    if (firstVisibleItem > mPrevFirstVisibleItem) {
                        //User is scrolling up
                        mCurScrollingDirection = ScrollDirection.UP;
                    } else if (firstVisibleItem < mPrevFirstVisibleItem) {
                        //User is scrolling down
                        mCurScrollingDirection = ScrollDirection.DOWN;
                    } else {
                        mCurScrollingDirection = ScrollDirection.SAME;
                    }
                    mPrevFirstVisibleItem = firstVisibleItem;
                }


                if (mIsLoadMoreEnabled && (mCurScrollingDirection == ScrollDirection.UP)) {
                    //We only need to paginate if user scrolling near the end of the list
                    if (!mPullCallback.isLoading() && !mPullCallback.hasLoadedAllItems()) {
                        //Only trigger a load more if a load operation is NOT happening AND all the items have not been loaded
                        final int totalItemCount = mRecyclerViewHelper.getItemCount();
                        final int firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
                        final int visibleItemCount = Math.abs(mRecyclerViewHelper.findLastVisibleItemPosition() - firstVisibleItem);
                        final int lastAdapterPosition = totalItemCount - 1;
                        final int lastVisiblePosition = (firstVisibleItem + visibleItemCount) - 1;
                        if (lastVisiblePosition >= (lastAdapterPosition - mLoadMoreOffset)) {
                            if (null != mPullCallback) {
                                mProgressBar.setVisibility(VISIBLE);
                                mPullCallback.onLoadMore();
                            }
                        }
                    }
                }
            }
        });
    }

    public void setComplete() {
        mProgressBar.setVisibility(GONE);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void initLoad() {
        if (null != mPullCallback) {
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            });
            mPullCallback.onRefresh();
        }
    }

    public void setColorSchemeResources(int... colorResIds) {
        mSwipeRefreshLayout.setColorSchemeResources(colorResIds);
    }

    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    public void setPullCallback(PullCallback mPullCallback) {
        this.mPullCallback = mPullCallback;
    }

    public void setLoadMoreOffset(int mLoadMoreOffset) {
        this.mLoadMoreOffset = mLoadMoreOffset;
    }

    public void isLoadMoreEnabled(boolean mIsLoadMoreEnabled) {
        this.mIsLoadMoreEnabled = mIsLoadMoreEnabled;
    }
}

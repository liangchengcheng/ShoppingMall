package cxsjcj.hdsx.com.activity.main;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cxsjcj.hdsx.com.activity.base.MyBaseActivity;
import cxsjcj.hdsx.com.activity.detail.ShoppingDetailActivity;
import cxsjcj.hdsx.com.adapter.JBApplicationAdapter;
import cxsjcj.hdsx.com.bean.AppInfo;
import cxsjcj.hdsx.com.bean.Jinbi;
import cxsjcj.hdsx.com.contacts.AppContacts;
import cxsjcj.hdsx.com.data.DataManager;
import cxsjcj.hdsx.com.ipl.PullCallback;
import cxsjcj.hdsx.com.ipl.ResponseCallback;
import cxsjcj.hdsx.com.itemanimation.CustomItemAnimator;
import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.view.LoadRecyclerView;
import cxsjcj.hdsx.com.view.dropdown.DropdownButton;
import cxsjcj.hdsx.com.view.dropdown.DropdownItemObject;
import cxsjcj.hdsx.com.view.dropdown.DropdownListView;
import cxsjcj.hdsx.com.view.dropdown.TopicLabelObject;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年10月25日13:33:50
 * Description:  金币界面的简单的介绍
 */
public class MainJBActivityActivity extends MyBaseActivity implements ResponseCallback {
    private static final int ID_TYPE_ALL = 0;
    private static final int ID_TYPE_MY = 1;
    private static final String TYPE_ALL = "自己交易";
    private static final String TYPE_MY = "客服帮忙";

    private static final int ID_LABEL_ALL = -1;
    private static final String LABEL_ALL = "全部用户";

    private static final String ORDER_REPLY_TIME = "最近上架";
    private static final String ORDER_PUBLISH_TIME = "最便宜";
    private static final String ORDER_HOT = "最信誉";
    private static final int ID_ORDER_REPLY_TIME = 51;
    private static final int ID_ORDER_PUBLISH_TIME = 49;
    private static final int ID_ORDER_HOT = 53;
    ListView listView;
    View mask;
    DropdownButton chooseType, chooseLabel, chooseOrder;
    DropdownListView dropdownType, dropdownLabel, dropdownOrder;
    Animation dropdown_in, dropdown_out, dropdown_mask_out;
    private List<TopicLabelObject> labels = new ArrayList<>();
    private DropdownButtonsController dropdownButtonsController = new DropdownButtonsController();
    private RecyclerView mRecyclerView;
    private JBApplicationAdapter mAdapter;
    private List<AppInfo> applicationList = new ArrayList<AppInfo>();
    private DataManager mDataManager = new DataManager();
    private SearchView searchView;
    private List<Jinbi> goldcounts = new ArrayList<>();
    private LoadRecyclerView mPullToLoadView;
    private boolean isLoading = false;
    private boolean isHasLoadedAll = false;
    private int nextPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPullToLoadView = (LoadRecyclerView) findViewById(R.id.pullToLoadView);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getColorPrimary());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        listView = (ListView) findViewById(R.id.listView);
        mask = findViewById(R.id.mask);
        chooseType = (DropdownButton) findViewById(R.id.chooseType);
        chooseLabel = (DropdownButton) findViewById(R.id.chooseLabel);
        chooseOrder = (DropdownButton) findViewById(R.id.chooseOrder);
        dropdownType = (DropdownListView) findViewById(R.id.dropdownType);
        dropdownLabel = (DropdownListView) findViewById(R.id.dropdownLabel);
        dropdownOrder = (DropdownListView) findViewById(R.id.dropdownOrder);
        dropdown_in = AnimationUtils.loadAnimation(this, R.anim.dropdown_in);
        dropdown_out = AnimationUtils.loadAnimation(this, R.anim.dropdown_out);
        dropdown_mask_out = AnimationUtils.loadAnimation(this, R.anim.dropdown_mask_out);
        dropdownButtonsController.init();
        //id count name
        TopicLabelObject topicLabelObject1 = new TopicLabelObject(1, 1, "个人");
        labels.add(topicLabelObject1);
        TopicLabelObject topicLabelObject2 = new TopicLabelObject(2, 1, "商户");
        labels.add(topicLabelObject2);
        TopicLabelObject topicLabelObject3 = new TopicLabelObject(2, 1, "Service");
        labels.add(topicLabelObject3);
        TopicLabelObject topicLabelObject4 = new TopicLabelObject(2, 1, "BroadcastReceiver");
        labels.add(topicLabelObject4);
        TopicLabelObject topicLabelObject5 = new TopicLabelObject(2, 1, "Activity");
        labels.add(topicLabelObject5);
        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdownButtonsController.hide();
            }
        });
        dropdownButtonsController.flushCounts();
        dropdownButtonsController.flushAllLabels();
        dropdownButtonsController.flushMyLabels();

        mRecyclerView = mPullToLoadView.getRecyclerView(); //(RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new CustomItemAnimator());
        mAdapter = new JBApplicationAdapter(new ArrayList<Jinbi>(), R.layout.row_application, MainJBActivityActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.SetOnItemClickListener(new JBApplicationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(MainJBActivityActivity.this, ShoppingDetailActivity.class));
            }
        });
        mPullToLoadView.isLoadMoreEnabled(true);
        mPullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {
                isLoading = true;
                mDataManager.loadNewData(AppContacts.JB_PATH + nextPage, MainJBActivityActivity.this);
            }

            @Override
            public void onRefresh() {
                isHasLoadedAll = false;
                mDataManager.loadNewData(AppContacts.JB_PATH + "1", MainJBActivityActivity.this);
            }

            @Override
            public boolean isLoading() {
                Log.e("main activity", "main isLoading:" + isLoading);
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return isHasLoadedAll;
            }
        });
        mPullToLoadView.initLoad();
    }

    @Override
    protected boolean isopen() {
        return true;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_main_jb;
    }

    /**
     * 获取主题的颜色
     *
     * @return颜色
     */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    /**
     * 创建一个menu
     *
     * @param menu
     * @return 是否创建成功
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mainjb, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        //searchItem.expandActionView();
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        ComponentName componentName = getComponentName();

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(componentName));
        searchView.setQueryHint(getString(R.string.search_note));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //recyclerAdapter.getFilter().filter(s);
                Toast.makeText(MainJBActivityActivity.this, "更新了", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
//                recyclerAdapter.setUpFactor();
//                refreshLayout.setEnabled(false);
                Toast.makeText(MainJBActivityActivity.this, "onMenuItemActionExpand", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // refreshLayout.setEnabled(true);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * 求求网络数据成功和失败的回调
     *   mDataManager.loadNewData(this);
     *   下面两句是增加的
     *              mDataManager.getData().add(0, photo);
                    waterFallAdapter.updateData(mDataManager.getData());
          下面2句是刷新的
           mDataManager.loadNewData(AaaActivity.this);
                    isLoadingData = true;
           下面的是加载更多
                   mDataManager.loadOldData(AaaActivity.this);

     * @param object
     */
    @Override
    public void onSuccess(Object object) {
        mAdapter.clearApplications();
        goldcounts = mDataManager.getData();
        mRecyclerView.setVisibility(View.VISIBLE);
        mAdapter.addApplications(goldcounts);
        mPullToLoadView.setComplete();
        isLoading = false;
        nextPage = nextPage + 1;
    }

    @Override
    public void onError(String msg) {
        isLoading = false;
        mPullToLoadView.setComplete();
        Toast.makeText(MainJBActivityActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
    }

    private class DropdownButtonsController implements DropdownListView.Container {
        private DropdownListView currentDropdownList;
        private List<DropdownItemObject> datasetType = new ArrayList<>(2);//全部讨论
        private List<DropdownItemObject> datasetAllLabel = new ArrayList<>();//全部标签
        private List<DropdownItemObject> datasetMyLabel = new ArrayList<>();//我的标签
        private List<DropdownItemObject> datasetLabel = datasetAllLabel;//标签集合   默认是全部标签
        private List<DropdownItemObject> datasetOrder = new ArrayList<>(3);//评论排序

        @Override
        public void show(DropdownListView view) {
            if (currentDropdownList != null) {
                currentDropdownList.clearAnimation();
                currentDropdownList.startAnimation(dropdown_out);
                currentDropdownList.setVisibility(View.GONE);
                currentDropdownList.button.setChecked(false);
            }
            currentDropdownList = view;
            mask.clearAnimation();
            mask.setVisibility(View.VISIBLE);
            currentDropdownList.clearAnimation();
            currentDropdownList.startAnimation(dropdown_in);
            currentDropdownList.setVisibility(View.VISIBLE);
            currentDropdownList.button.setChecked(true);
        }

        @Override
        public void hide() {
            if (currentDropdownList != null) {
                currentDropdownList.clearAnimation();
                currentDropdownList.startAnimation(dropdown_out);
                currentDropdownList.button.setChecked(false);
                mask.clearAnimation();
                mask.startAnimation(dropdown_mask_out);
            }
            currentDropdownList = null;
        }

        @Override
        public void onSelectionChanged(DropdownListView view) {
            if (view == dropdownType) {
                updateLabels(getCurrentLabels());
            }

        }

        void reset() {
            chooseType.setChecked(false);
            chooseLabel.setChecked(false);
            chooseOrder.setChecked(false);

            dropdownType.setVisibility(View.GONE);
            dropdownLabel.setVisibility(View.GONE);
            dropdownOrder.setVisibility(View.GONE);
            mask.setVisibility(View.GONE);

            dropdownType.clearAnimation();
            dropdownLabel.clearAnimation();
            dropdownOrder.clearAnimation();
            mask.clearAnimation();
        }

        void init() {
            reset();
            datasetType.add(new DropdownItemObject(TYPE_ALL, ID_TYPE_ALL, "all"));
            datasetType.add(new DropdownItemObject(TYPE_MY, ID_TYPE_MY, "my"));

            dropdownType.bind(datasetType, chooseType, this, ID_TYPE_ALL);

            datasetAllLabel.add(new DropdownItemObject(LABEL_ALL, ID_LABEL_ALL, null) {
                @Override
                public String getSuffix() {
                    return dropdownType.current == null ? "" : dropdownType.current.getSuffix();
                }
            });
            datasetMyLabel.add(new DropdownItemObject(LABEL_ALL, ID_LABEL_ALL, null));
            datasetLabel = datasetAllLabel;
            dropdownLabel.bind(datasetLabel, chooseLabel, this, ID_LABEL_ALL);

            datasetOrder.add(new DropdownItemObject(ORDER_REPLY_TIME, ID_ORDER_REPLY_TIME, "51"));
            datasetOrder.add(new DropdownItemObject(ORDER_PUBLISH_TIME, ID_ORDER_PUBLISH_TIME, "49"));
            datasetOrder.add(new DropdownItemObject(ORDER_HOT, ID_ORDER_HOT, "53"));
            dropdownOrder.bind(datasetOrder, chooseOrder, this, ID_ORDER_REPLY_TIME);

            dropdown_mask_out.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (currentDropdownList == null) {
                        reset();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

        private List<DropdownItemObject> getCurrentLabels() {
            return dropdownType.current != null && dropdownType.current.id == ID_TYPE_MY ? datasetMyLabel : datasetAllLabel;
        }

        void updateLabels(List<DropdownItemObject> targetList) {
            if (targetList == getCurrentLabels()) {
                datasetLabel = targetList;
                dropdownLabel.bind(datasetLabel, chooseLabel, this, dropdownLabel.current.id);
            }
        }

        public void flushCounts() {

            datasetType.get(ID_TYPE_ALL).setSuffix(" (" + "5" + ")");
            datasetType.get(ID_TYPE_MY).setSuffix(" (" + "3" + ")");
            dropdownType.flush();
            dropdownLabel.flush();
        }

        void flushAllLabels() {
            flushLabels(datasetAllLabel);
        }

        void flushMyLabels() {
            flushLabels(datasetMyLabel);
        }

        private void flushLabels(List<DropdownItemObject> targetList) {

            while (targetList.size() > 1) targetList.remove(targetList.size() - 1);
            for (int i = 0, n = 5; i < n; i++) {

                int id = labels.get(i).getId();
                String name = labels.get(i).getName();
                if (TextUtils.isEmpty(name)) continue;
                int topicsCount = labels.get(i).getCount();
                // 只有all才做0数量过滤，因为my的返回数据总是0
                if (topicsCount == 0 && targetList == datasetAllLabel) continue;
                DropdownItemObject item = new DropdownItemObject(name, id, String.valueOf(id));
                if (targetList == datasetAllLabel)
                    item.setSuffix("(5)");
                targetList.add(item);
            }
            updateLabels(targetList);
        }
    }

    private class InitializeApplicationsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mAdapter.clearApplications();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            applicationList.clear();
            //Query the applications
            final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> ril = getPackageManager().queryIntentActivities(mainIntent, 0);
            for (ResolveInfo ri : ril) {
                applicationList.add(new AppInfo(MainJBActivityActivity.this, ri));
            }
            Collections.sort(applicationList);

            for (AppInfo appInfo : applicationList) {
                //load icons before shown. so the list is smoother
                appInfo.getIcon();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //handle visibility
            mRecyclerView.setVisibility(View.VISIBLE);
            //set data for list
            mAdapter.addApplications(goldcounts);
            super.onPostExecute(result);
        }
    }
}

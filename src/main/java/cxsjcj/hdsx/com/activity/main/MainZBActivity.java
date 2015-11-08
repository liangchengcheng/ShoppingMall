package cxsjcj.hdsx.com.activity.main;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import cxsjcj.hdsx.com.activity.base.MyBaseActivity;
import cxsjcj.hdsx.com.activity.detail.ShoppingMainActivity;
import cxsjcj.hdsx.com.adapter.MyAdapter;
import cxsjcj.hdsx.com.adapter.SubAdapter;
import cxsjcj.hdsx.com.adapter.ZBHomeGridAdapter;
import cxsjcj.hdsx.com.bean.Zhuangbei;
import cxsjcj.hdsx.com.ipl.OnRcvScrollListener;
import cxsjcj.hdsx.com.itemanimation.AlphaInAnimationAdapter;
import cxsjcj.hdsx.com.itemanimation.FadeInAnimator;
import cxsjcj.hdsx.com.itemanimation.ScaleInAnimationAdapter;
import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.myapplication.NetApplication;
import cxsjcj.hdsx.com.network.api.ApiRequest;
import cxsjcj.hdsx.com.network.api.GsonGetRequest;

/**
 * @author 梁铖城
 * @date 2015年8月22日18:24:46
 * @description 装备的界面
 */
public class MainZBActivity extends MyBaseActivity implements OnDismissListener {
    private LinearLayout ll_quyu, ll_jiage, ll_huxing, lv1_layout;
    private ListView lv1, lv2;
    private TextView quyu, huxing, jiage;
    private ImageView icon1, icon2, icon3;
    private int screenWidth;
    private int screenHeight;
    private int idx;
    private SubAdapter subAdapter;
    private String cities[][];
    private MyAdapter madapter;
    private SearchView searchView;
    private static final String mTAG = "TagTwo";
    private RecyclerView recyclerView;
    private boolean isLoading = false;
    private boolean isHasLoadedAll = false;
    private int nextPage = 1;
    private ZBHomeGridAdapter adapter;
    boolean mIsFirstTimeTouchBottom = true;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mPullToLoadView= (LoadRecyclerView) findViewById(R.id.pullToLoadView);
        /*添加帅选的条件用二维数组，2次选择*/
        cities = new String[][]{
                getResources().getStringArray(R.array.guangdong),
                null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null};
        /*toolbar相关的代码*/
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getColorPrimary());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);//mPullToLoadView.getRecyclerView();
        //recyclerView.setLayoutManager(new GridLayoutManager(MainZBActivity.this, 2));
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        final GridLayoutManager layoutManager = new GridLayoutManager(MainZBActivity.this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new FadeInAnimator());
        recyclerView.addOnScrollListener(new OnRcvScrollListener() {
            @Override
            public void onBottom() {
                super.onBottom();
                // 到底部自动加载
                mSwipeRefreshLayout.setRefreshing(true);

                loadData(nextPage);
            }
        });
        mSwipeRefreshLayout.setRefreshing(true);
        loadData(1);
        /*关于筛选的条件的代码*/
        ll_quyu = (LinearLayout) findViewById(R.id.ll_quyu);
        ll_quyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idx = 1;
                icon1.setImageResource(R.drawable.icon_43343434);
                showPopupWindow(findViewById(R.id.ll_layout), 1);
            }
        });

        ll_jiage = (LinearLayout) findViewById(R.id.ll_jiage);
        ll_jiage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                idx = 2;
                icon2.setImageResource(R.drawable.icon_43343434);
                showPopupWindow(findViewById(R.id.ll_layout), 2);

            }
        });

        ll_huxing = (LinearLayout) findViewById(R.id.ll_huxing);
        ll_huxing.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                idx = 3;
                icon3.setImageResource(R.drawable.icon_43343434);
                showPopupWindow(findViewById(R.id.ll_layout), 3);
            }
        });

        quyu = (TextView) findViewById(R.id.quyu);
        huxing = (TextView) findViewById(R.id.huxing);
        jiage = (TextView) findViewById(R.id.jiage);
        icon1 = (ImageView) findViewById(R.id.icon1);
        icon2 = (ImageView) findViewById(R.id.icon2);
        icon3 = (ImageView) findViewById(R.id.icon3);
        initScreenWidth();
    }

    @Override
    protected boolean isopen() {
        return true;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_main_zb;
    }

    private void loadData(final int page) {
        isLoading = true;
        if (isHasLoadedAll) {
            Toast.makeText(MainZBActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
        }
        if (page > 10) {
            isHasLoadedAll = true;
            return;
        }
        final GsonGetRequest<ArrayList<Zhuangbei>> gsonGetRequest = ApiRequest.getDummyObjectArray(
                new Response.Listener<ArrayList<Zhuangbei>>() {
                    @Override
                    public void onResponse(ArrayList<Zhuangbei> dummyObjectArrayList) {
                        Log.e("lcc", dummyObjectArrayList.size() + "");
                        setData(dummyObjectArrayList, page);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("lcc", error.toString());
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, page
        );
        NetApplication.addRequest(gsonGetRequest, mTAG);
    }


    @Override
    protected void onStop() {
        NetApplication.cancelAllRequests(mTAG);
        super.onStop();
    }

    /**
     * 设置数据现实到控件上
     * @param  给recyclerview设置数据适配器
     */
    private void setData(ArrayList<Zhuangbei> dummyObjectArrayList, int page) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (page == 1) {
            adapter = new ZBHomeGridAdapter(MainZBActivity.this, dummyObjectArrayList);
            AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
            ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
            //scaleAdapter.setFirstOnly(false);
            //scaleAdapter.setInterpolator(new OvershootInterpolator());
            recyclerView.setAdapter(scaleAdapter);
            adapter.setOnItemClickListener(new ZBHomeGridAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, Zhuangbei data) {
                    Snackbar.make(view, data.getJiage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent intent = new Intent(MainZBActivity.this, ShoppingMainActivity.class);
                    intent.putExtra("zhuangbei", data);
                    startActivity(intent);
                }
            });
        }
        if (page > 1) {
            if (adapter != null) {
                adapter.add(dummyObjectArrayList);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }
        isLoading = false;
        nextPage++;
    }

    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    /*创建一个菜单并且添加searchview*/
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
                Toast.makeText(MainZBActivity.this, "更新了", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
//                recyclerAdapter.setUpFactor();
//                refreshLayout.setEnabled(false);
                Toast.makeText(MainZBActivity.this, "onMenuItemActionExpand", Toast.LENGTH_SHORT).show();
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

    public void showPopupWindow(View anchor, int flag) {
        final PopupWindow popupWindow = new PopupWindow(MainZBActivity.this);
        View contentView = LayoutInflater.from(MainZBActivity.this).inflate(
                R.layout.windows_popupwindow, null);
        lv1 = (ListView) contentView.findViewById(R.id.lv1);
        lv2 = (ListView) contentView.findViewById(R.id.lv2);
        lv1_layout = (LinearLayout) contentView.findViewById(R.id.lv_layout);
        switch (flag) {
            case 1:
                madapter = new MyAdapter(MainZBActivity.this,
                        initArrayData(R.array.quyu));
                break;
            case 2:
                madapter = new MyAdapter(MainZBActivity.this,
                        initArrayData(R.array.zongjia));
                break;
            case 3:
                madapter = new MyAdapter(MainZBActivity.this,
                        initArrayData(R.array.huxing));
                break;
        }
        lv1.setAdapter(madapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (parent.getAdapter() instanceof MyAdapter) {
                    madapter.setSelectItem(position);
                    madapter.notifyDataSetChanged();
                    lv2.setVisibility(View.INVISIBLE);
                    if (lv2.getVisibility() == View.INVISIBLE) {
                        lv2.setVisibility(View.VISIBLE);
                        switch (idx) {
                            case 1:
                                lv1_layout.getLayoutParams().width = 0;
                                if (cities[position] != null) {
                                    subAdapter = new SubAdapter(
                                            MainZBActivity.this,
                                            cities[position]);
                                } else {
                                    subAdapter = null;
                                }
                                break;
                            case 2:
                                lv1_layout.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;

                                break;
                            case 3:
                                lv1_layout.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
                                break;
                        }
                        if (subAdapter != null) {
                            lv2.setAdapter(subAdapter);
                            subAdapter.notifyDataSetChanged();
                            lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent,
                                                        View view, int position, long id) {
                                    String name = (String) parent.getAdapter()
                                            .getItem(position);
                                    setHeadText(idx, name);
                                    popupWindow.dismiss();
                                    subAdapter = null;
                                }
                            });
                        } else {
                            String name = (String) parent.getAdapter().getItem(
                                    position);
                            setHeadText(idx, name);
                            popupWindow.dismiss();
                        }

                    }
                }
            }
        });
        popupWindow.setOnDismissListener(this);
        popupWindow.setWidth(screenWidth);
        popupWindow.setHeight(screenHeight);
        popupWindow.setContentView(contentView);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        popupWindow.showAsDropDown(anchor);
    }

    /**
     * @return void 返回类型
     * @Title: setHeadText
     * @Description: 点击之后设置在上边的TextView里
     */
    private void setHeadText(int idx, String text) {
        switch (idx) {
            case 1:
                quyu.setText(text);
                Toast.makeText(MainZBActivity.this, text, Toast.LENGTH_SHORT).show();
                break;
            case 2:
                jiage.setText(text);
                Toast.makeText(MainZBActivity.this, text, Toast.LENGTH_SHORT).show();
                break;
            case 3:
                huxing.setText(text);
                Toast.makeText(MainZBActivity.this, text, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * @return void 返回类型
     * @Title: initScreenWidth
     * @Description: 查看自身的宽高
     */
    private void initScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
        Log.v("屏幕宽高", "宽度" + screenWidth + "高度" + screenHeight);
    }

    /**
     * @return 返回字符串集合
     * @Title: 添加数据
     * @Description: 读取字符串数组的数据添加到适配器里面
     */
    private List<String> initArrayData(int id) {
        List<String> list = new ArrayList<String>();
        String[] array = this.getResources().getStringArray(id);
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }

    /**
     * @return 无返回类型
     * @Title: 关闭的时候
     * @Description: 设置图片
     */
    @Override
    public void onDismiss() {
        icon1.setImageResource(R.drawable.icon_435);
        icon2.setImageResource(R.drawable.icon_435);
        icon3.setImageResource(R.drawable.icon_435);
    }

}

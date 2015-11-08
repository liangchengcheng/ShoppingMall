package cxsjcj.hdsx.com.activity.detail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.itangqi.greendao.Note;
import com.itangqi.greendao.NoteDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cxsjcj.hdsx.com.activity.base.MyBaseActivity;
import cxsjcj.hdsx.com.activity.shoppingcar.ShoppingCarActivity;
import cxsjcj.hdsx.com.bean.Zhuangbei;
import cxsjcj.hdsx.com.fragment.ViewPagerFragment;
import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.myapplication.NetApplication;
import cxsjcj.hdsx.com.widget.ImageInfo;
import cxsjcj.hdsx.com.widget.PhotoView;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年10月15日22:38:04
 * Description:  商品详情界面
 */
// TODO: 2015/10/14 进度：70%，格式：合格，注意：无 ，未完成模块：界面进一步优化
public class ShoppingMainActivity extends MyBaseActivity {
    private Zhuangbei zhuangbeibean;
    private PhotoView iv_shopping_thing;
    private Cursor cursor;
    public static final String TAG = "DaoExample";
    private ImageView mAnimImageView;
    private Animation mAnimation;
    private ArrayList<String> imgList = new ArrayList<>();
    private ArrayList<ImageInfo> imgImageInfos = new ArrayList<>();
    private CardView firstView;
    private FrameLayout secondView;
    private int fHeight;
    private int sHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootview = findViewById(R.id.rootview);
        firstView = (CardView)findViewById(R.id.cd_first);
        secondView = (FrameLayout)findViewById(R.id.second_view);
        if (savedInstanceState == null) {
            rootview.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    rootview.getViewTreeObserver().removeOnPreDrawListener(this);
                    startAnimation();
                    return true;
                }
            });
        }
        firstView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                fHeight = firstView.getHeight();
                firstView.getViewTreeObserver()
                        .removeGlobalOnLayoutListener(this);
            }
        });
        secondView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                sHeight = secondView.getHeight();
                secondView.getViewTreeObserver()
                        .removeGlobalOnLayoutListener(this);
            }
        });

        mAnimImageView = (ImageView) findViewById(R.id.cart_anim_icon);
        final Intent intent = getIntent();
        zhuangbeibean = (Zhuangbei) intent.getSerializableExtra("zhuangbei");
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getColorPrimary());
        toolbar.setTitle("商品详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        iv_shopping_thing = (PhotoView) findViewById(R.id.iv_shopping_thing);
        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        TextView tv_price = (TextView) findViewById(R.id.tv_price);
        if (zhuangbeibean != null) {
            imgList.add(0, zhuangbeibean.getImage());
            Glide.with(ShoppingMainActivity.this)
                    .load(zhuangbeibean.getImage())
                    .centerCrop()
                    .placeholder(R.drawable.loading1)
                    .crossFade()
                    .into(iv_shopping_thing);
            tv_name.setText(zhuangbeibean.getTitle());
            tv_price.setText(zhuangbeibean.getJiage() + "元");
            iv_shopping_thing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("imgs", imgList);
                    bundle.putParcelable("info", (iv_shopping_thing).getInfo());
                    bundle.putInt("position", 0);
                    imgImageInfos.clear();
                    for (int i = 0; i < imgList.size(); i++) {
                        imgImageInfos.add(iv_shopping_thing.getInfo());
                    }
                    bundle.putParcelableArrayList("infos", imgImageInfos);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_viewpager, ViewPagerFragment.getInstance(bundle), "ViewPagerFragment")
                            .addToBackStack(null).commit();
                }
            });
        }

        findViewById(R.id.project_home_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShoppingMainActivity.this, ShoppingTrueActivity.class));
            }
        });
        findViewById(R.id.btn_anim3_hidden).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initHiddenAnim();
            }
        });
    }

    @Override
    protected boolean isopen() {
        return true;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_shopping_main;
    }

    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shopping_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                search();
                break;
            case R.id.action_shoppingcar:
                // Dialog pricedialog = new ShoppingCarDialog(ShoppingMainActivity.this);
                //pricedialog.show();

                initShowAnim();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 插入操作简单到只要你创建一个 Java 对象
    private void addNote(Zhuangbei zb) {
        mAnimation = AnimationUtils.loadAnimation(ShoppingMainActivity.this, R.anim.cart_anim);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mAnimImageView.setVisibility(View.INVISIBLE);
                new MaterialDialog.Builder(ShoppingMainActivity.this)
                        .iconRes(R.drawable.ic_launcher)
                        .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                        .title("添加成功")
                        .content("你的的商品已经成功添加至购物车，请选择你的下一步操作")
                        .positiveText("购物车")
                        .negativeText("再看看")
                        .cancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {

                            }
                        })
                        .dismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                Intent intent = new Intent(ShoppingMainActivity.this, ShoppingCarActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        });
        mAnimImageView.setVisibility(View.VISIBLE);
        mAnimImageView.startAnimation(mAnimation);
        long id = new Random().nextLong();
        Note note = new Note(id, zb.getImage(),
                changeString(zb.getJiage()), changeString(zb.getMiaoshu()),
                changeString(zb.getShangpinid()), changeString(zb.getShijian()),
                changeString(zb.getTitle()), changeString(zb.getDealid()),
                changeString(zb.getBuyid()), changeString(zb.getZhuangtai()),
                changeString(zb.getLianxi()),
                changeString(zb.getQufu()), changeString(zb.getJiaoyifangshi()));
        getNoteDao().insert(note);
    }

    private String changeString(String vale) {
        if (vale == null || "".equals(vale)) {
            return "暂时没有值";
        }
        return  vale;
    }

    private void search() {
        // Query 类代表了一个可以被重复执行的查询
        Query query = getNoteDao().queryBuilder()
                .build();
        // 查询结果以 List 返回
        List notes = query.list();
        Toast.makeText(ShoppingMainActivity.this, notes.size() + "", Toast.LENGTH_SHORT).show();
        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }


    private NoteDao getNoteDao() {
        // 通过 BaseApplication 类提供的 getDaoSession() 获取具体 Dao
        return ((NetApplication) this.getApplicationContext()).getDaoSession().getNoteDao();
    }

    private SQLiteDatabase getDb() {
        // 通过 BaseApplication 类提供的 getDb() 获取具体 db
        return ((NetApplication) this.getApplicationContext()).getDb();
    }

    private View rootview;
    private void startAnimation() {
        rootview.setScaleY(0.1f);
        //设置一个中心移动
        rootview.setPivotY(rootview.getY() + rootview.getHeight() / 2);
        rootview.animate().scaleY(1).setDuration(500).setInterpolator(new AccelerateInterpolator()).start();
        rootview.animate().scaleY(1)
                .setDuration(500).setInterpolator(new AccelerateInterpolator()).start();
    }

    private void initShowAnim(){
        ObjectAnimator fViewScaleXAnim=ObjectAnimator.ofFloat(firstView,"scaleX",1.0f,0.8f);
        fViewScaleXAnim.setDuration(350);
        ObjectAnimator fViewScaleYAnim=ObjectAnimator.ofFloat(firstView,"scaleY",1.0f,0.8f);
        fViewScaleYAnim.setDuration(350);
        ObjectAnimator fViewAlphaAnim=ObjectAnimator.ofFloat(firstView,"alpha",1.0f,0.5f);
        fViewAlphaAnim.setDuration(350);
        ObjectAnimator fViewRotationXAnim = ObjectAnimator.ofFloat(firstView, "rotationX", 0f, 10f);
        fViewRotationXAnim.setDuration(200);
        ObjectAnimator fViewResumeAnim = ObjectAnimator.ofFloat(firstView, "rotationX", 10f, 0f);
        fViewResumeAnim.setDuration(150);
        fViewResumeAnim.setStartDelay(200);
        ObjectAnimator fViewTransYAnim=ObjectAnimator.ofFloat(firstView,"translationY",0,-0.1f* fHeight);
        fViewTransYAnim.setDuration(350);
        ObjectAnimator sViewTransYAnim=ObjectAnimator.ofFloat(secondView,"translationY",sHeight,0);
        sViewTransYAnim.setDuration(350);
        sViewTransYAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                secondView.setVisibility(View.VISIBLE);
            }
        });
        AnimatorSet showAnim=new AnimatorSet();
        showAnim.playTogether(fViewScaleXAnim,fViewRotationXAnim,fViewResumeAnim,fViewTransYAnim,fViewAlphaAnim,fViewScaleYAnim,sViewTransYAnim);
        showAnim.start();
    }

    private void initHiddenAnim(){
        addNote(zhuangbeibean);
        ObjectAnimator fViewScaleXAnim=ObjectAnimator.ofFloat(firstView,"scaleX",0.8f,1.0f);
        fViewScaleXAnim.setDuration(350);
        ObjectAnimator fViewScaleYAnim=ObjectAnimator.ofFloat(firstView,"scaleY",0.8f,1.0f);
        fViewScaleYAnim.setDuration(350);
        ObjectAnimator fViewAlphaAnim=ObjectAnimator.ofFloat(firstView,"alpha",0.5f,1.0f);
        fViewAlphaAnim.setDuration(350);
        ObjectAnimator fViewRotationXAnim = ObjectAnimator.ofFloat(firstView, "rotationX", 0f, 10f);
        fViewRotationXAnim.setDuration(200);
        ObjectAnimator fViewResumeAnim = ObjectAnimator.ofFloat(firstView, "rotationX", 10f, 0f);
        fViewResumeAnim.setDuration(150);
        fViewResumeAnim.setStartDelay(200);
        ObjectAnimator fViewTransYAnim=ObjectAnimator.ofFloat(firstView,"translationY",-0.1f* fHeight,0);
        fViewTransYAnim.setDuration(350);
        ObjectAnimator sViewTransYAnim=ObjectAnimator.ofFloat(secondView,"translationY",0,sHeight);
        sViewTransYAnim.setDuration(350);
        sViewTransYAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                secondView.setVisibility(View.INVISIBLE);
            }
        });
        AnimatorSet showAnim=new AnimatorSet();
        showAnim.playTogether(fViewScaleXAnim,fViewRotationXAnim,fViewResumeAnim,fViewTransYAnim,fViewAlphaAnim,fViewScaleYAnim,sViewTransYAnim);
        showAnim.start();
    }

}

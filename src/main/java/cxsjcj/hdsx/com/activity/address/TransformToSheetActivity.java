package cxsjcj.hdsx.com.activity.address;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.konifar.fab_transformation.FabTransformation;


import butterknife.Bind;
import butterknife.OnClick;
import cxsjcj.hdsx.com.myapplication.R;

public class TransformToSheetActivity extends BaseActivity {

    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.sheet)
    View sheet;
    @Bind(R.id.overlay)
    View overlay;

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, TransformToSheetActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        context.startActivity(intent);
    }

    @Override
    int getLayoutResId() {
        return R.layout.activity_transform_to_sheet;
    }

    @Override
    protected void initView() {
        //
    }

    @OnClick(R.id.fab)
    void onClickFab() {
        if (fab.getVisibility() == View.VISIBLE) {
            FabTransformation.with(fab).setOverlay(overlay).transformTo(sheet);
        }
    }

    @OnClick(R.id.row_1)
    void onClickRow1() {
        AppUtil.showToast("Row 1 clicked!", this);
    }

    @OnClick(R.id.row_2)
    void onClickRow2() {
        AppUtil.showToast("Row 2 clicked!", this);
    }

    @OnClick(R.id.row_3)
    void onClickRow3() {
        AppUtil.showToast("Row 3 clicked!", this);
    }

    @OnClick(R.id.row_4)
    void onClickRow4() {
        AppUtil.showToast("Row 4 clicked!", this);
    }

    @OnClick(R.id.row_5)
    void onClickRow5() {
        AppUtil.showToast("Row 5 clicked!", this);
    }

    @OnClick(R.id.overlay)
    void onClickOverlay() {
        if (fab.getVisibility() != View.VISIBLE) {
            FabTransformation.with(fab).setOverlay(overlay).transformFrom(sheet);
        }
    }

    @Override
    public void onBackPressed() {
        if (fab.getVisibility() != View.VISIBLE) {
            FabTransformation.with(fab).setOverlay(overlay).transformFrom(sheet);
            return;
        }
        super.onBackPressed();
    }

}

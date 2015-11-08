package cxsjcj.hdsx.com.test.listanimation;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

import cxsjcj.hdsx.com.itemanimation.AlphaInAnimationAdapter;
import cxsjcj.hdsx.com.itemanimation.BaseItemAnimator;
import cxsjcj.hdsx.com.itemanimation.FadeInAnimator;
import cxsjcj.hdsx.com.itemanimation.ScaleInAnimationAdapter;
import cxsjcj.hdsx.com.myapplication.R;

/**
 * 动画的测试
 */
public class Main3Activity extends AppCompatActivity {
    private BaseItemAnimator mAnimator;


    public BaseItemAnimator getAnimator() {
        return mAnimator;
    }


private static String[] data = new String[]{
        "Apple", "Ball", "Camera", "Day", "Egg", "Foo", "Google", "Hello", "Iron", "Japan",
        "Coke", "Dog", "Cat", "Yahoo", "Sony", "Canon", "Fujitsu", "USA", "Nexus", "LINE",
        "Haskell", "C++", "Java", "Go", "Swift", "Objective-c", "Ruby", "PHP", "Bash", "ksh",
        "C", "Groovy", "Kotlin"
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);

//        if (getIntent().getBooleanExtra("GRID", true)) {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        } else {
      //      recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        }

        recyclerView.setItemAnimator(new FadeInAnimator());
        MainAdapter adapter = new MainAdapter(this, new ArrayList<>(Arrays.asList(data)));
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
//        scaleAdapter.setFirstOnly(false);
//        scaleAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(scaleAdapter);
    }
}

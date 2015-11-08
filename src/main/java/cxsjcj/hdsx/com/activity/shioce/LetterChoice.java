package cxsjcj.hdsx.com.activity.shioce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cxsjcj.hdsx.com.myapplication.R;

public class LetterChoice extends AppCompatActivity {
    LetterChoiceAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_choice);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getColorPrimary());
        toolbar.setTitle("请选择你所在的城市");
        toolbar.setTitleTextColor(getColor(R.color.action_bar_title_color));
        toolbar.collapseActionView();
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView= (ListView) findViewById(R.id.lv_content);
        initData("北边");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent=new Intent();
                intent.putExtra("result",adapter.getItem(position).toString());
                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }
    private void initData(String text)
    {
        adapter = new LetterChoiceAdapter(LetterChoice.this,
                initArrayData(R.array.shandong));
        listView.setAdapter(adapter);
    }
    private List<String> initArrayData(int id) {
        List<String> list = new ArrayList<String>();
        String[] array = this.getResources().getStringArray(id);
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }
    protected int getColor(int res){
        if (res <= 0)
            throw new IllegalArgumentException("resource id can not be less 0");
        return getResources().getColor(res);
    }
    public int getColorPrimary(){
        TypedValue typedValue = new  TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}

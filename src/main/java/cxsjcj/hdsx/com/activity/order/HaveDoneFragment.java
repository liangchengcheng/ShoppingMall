package cxsjcj.hdsx.com.activity.order;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itangqi.greendao.NoteDao;

import java.util.List;

import cxsjcj.hdsx.com.adapter.HaveDoneOrderAdapter;
import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.myapplication.NetApplication;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * @author: 梁铖城
 * @date: 2015年9月3日23:37:22
 * @description: 已经完成的订单
 * @blog: http:www.17yxb.cn
 */
public class HaveDoneFragment  extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.have_done_fragment,null);

        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        search();
    }

    private void search() {
        // Query 类代表了一个可以被重复执行的查询
        Query query = getNoteDao().queryBuilder()
                .build();
        // 查询结果以 List 返回
        List notes = query.list();
        if (notes != null && notes.size() > 0) {
            HaveDoneOrderAdapter adapter=new HaveDoneOrderAdapter(getActivity(),notes);
            recyclerView.setAdapter(adapter);
        }
        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }


    private NoteDao getNoteDao() {
        // 通过 BaseApplication 类提供的 getDaoSession() 获取具体 Dao
        return ((NetApplication) getActivity().getApplicationContext()).getDaoSession().getNoteDao();
    }

    private SQLiteDatabase getDb() {
        // 通过 BaseApplication 类提供的 getDb() 获取具体 db
        return ((NetApplication)  getActivity().getApplicationContext()).getDb();
    }
}

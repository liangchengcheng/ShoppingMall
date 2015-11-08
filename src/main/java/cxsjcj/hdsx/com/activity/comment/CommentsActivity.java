package cxsjcj.hdsx.com.activity.comment;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.Bind;
import cxsjcj.hdsx.com.myapplication.R;


/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年10月4日17:47:47
 * Description:  关于评论的
 */
public class CommentsActivity extends AppCompatActivity  {

    public static final String ARG_ORAWING_START_LOCATION = "arg_drawing_start_location";

    @Bind(R.id.contentRoot)
    LinearLayout contentRoot;
    @Bind(R.id.rvComments)
    RecyclerView rvComments;
    @Bind(R.id.llAddComment)
    LinearLayout llAddComment;
    @Bind(R.id.etComment)
    EditText etComment;

}

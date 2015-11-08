package cxsjcj.hdsx.com.activity.shioce;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import cxsjcj.hdsx.com.myapplication.R;


public class LetterListView extends FrameLayout {

    private final int MSG_HIDE_LETTER = 0x0;


    private final int LETTER_LIST_VIEW_WIDTH = 50;

    private ListView mListView;
    private LetterBaseAdapter mAdapter;

    private ListView mLetterListView;
    private LetterAdapter mLetterAdapter;

    private TextView mLetterTextView;

    private Handler mLetterhandler;

    public LetterListView(Context context) {
        super(context);
        initListView(context);
    }

    public LetterListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initListView(context);
    }

    private void initListView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        mListView = (ListView) inflater.inflate(R.layout.letter_list_container, null, false);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mListView, lp);
        mLetterListView = (ListView) inflater.inflate(R.layout.letter_list_letter, null, false);
        mLetterListView.setOnTouchListener(mLetterOnTouchListener);
        LayoutParams letterListLp = new LayoutParams(LETTER_LIST_VIEW_WIDTH, LayoutParams.MATCH_PARENT, Gravity.RIGHT);
        addView(mLetterListView, letterListLp);
        mLetterTextView = (TextView) inflater.inflate(R.layout.letter_list_position, null, false);
        LayoutParams letterLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        addView(mLetterTextView, letterLp);
        mLetterTextView.setVisibility(View.INVISIBLE);

        mLetterhandler = new LetterHandler(this);
    }

    public void setAdapter(LetterBaseAdapter adapter) {
        if (adapter != null) {
            mAdapter = adapter;
            mListView.setAdapter(mAdapter);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mListView.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mLetterAdapter = new LetterAdapter(h - getPaddingTop() - getPaddingBottom());
        mLetterListView.setAdapter(mLetterAdapter);
    }

    private void showLetter(String letter) {
        if (mLetterTextView.getVisibility() != View.VISIBLE) {
            mLetterTextView.setVisibility(View.VISIBLE);
            mLetterListView.setBackgroundResource(android.R.color.darker_gray);
        }
        mLetterTextView.setText(letter);

        mLetterhandler.removeMessages(MSG_HIDE_LETTER);
        mLetterhandler.sendEmptyMessageDelayed(MSG_HIDE_LETTER, 500);
    }

    private void handleLetterMessage(Message msg) {
        mLetterTextView.setVisibility(View.INVISIBLE);
        mLetterListView.setBackgroundResource(android.R.color.white);
    }

    private OnTouchListener mLetterOnTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int height = (int) event.getY() - v.getTop();

            int position = mLetterAdapter.getTouchPoistion(height);
            if (position >= 0) {
                char letter = (Character) mLetterAdapter.getItem(position);
                showLetter(String.valueOf(letter));
                int select = mAdapter.getIndex(letter);
                if (select >= 0) {
                    mListView.setSelection(select);
                }
                return true;
            }
            return false;
        }
    };

    private class LetterAdapter extends BaseAdapter {

        private static final String LETTER_STR = "+ABCDEFGHIJKLMNOPQRSTUVWXYZ#";

        private char[] letterArray;

        private int itemHeight;

        public LetterAdapter(int height) {
            if (mAdapter.hideLetterNotMatch()) {
                List<Character> list = new ArrayList<Character>();
                char[] allArray = LETTER_STR.toCharArray();
                for (int i = 0; i < allArray.length; i++) {
                    char letter = allArray[i];
                    int position = mAdapter.getIndex(letter);
                    if (position >= 0) {
                        list.add(letter);
                    }
                }
                letterArray = new char[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    letterArray[i] = list.get(i);
                }
                list.clear();
                list = null;
            } else {
                letterArray = LETTER_STR.toCharArray();
            }
            itemHeight = height / letterArray.length;
        }

        @Override
        public int getCount() {
            return letterArray.length;
        }

        @Override
        public Object getItem(int position) {
            return letterArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new TextView(getContext());
                ((TextView) convertView).setTextColor(getResources().getColor(android.R.color.black));
                ((TextView) convertView).setTextSize(16);
                ((TextView) convertView).setGravity(Gravity.CENTER);
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                        AbsListView.LayoutParams.MATCH_PARENT, itemHeight);
                convertView.setLayoutParams(lp);
            }
            ((TextView) convertView).setText(String.valueOf(letterArray[position]));

            return convertView;
        }

        public int getTouchPoistion(int touchHeight) {
            int position = touchHeight / itemHeight;
            if (position >= 0 && position < getCount()) {
                return position;
            }
            return -1;
        }
    }

    private static class LetterHandler extends Handler {

        private SoftReference<LetterListView> srLetterListView;

        public LetterHandler(LetterListView letterListView) {
            srLetterListView = new SoftReference<LetterListView>(letterListView);
        }

        @Override
        public void handleMessage(Message msg) {
            LetterListView letterListView = srLetterListView.get();
            if (letterListView != null) {
                letterListView.handleLetterMessage(msg);
            }
        }
    }
}

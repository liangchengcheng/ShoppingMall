package cxsjcj.hdsx.com.activity.shioce;

import android.widget.BaseAdapter;


public abstract class LetterBaseAdapter extends BaseAdapter
{

    protected static final char HEADER = '+';

    protected static final char FOOTER = '#';

    public abstract boolean hideLetterNotMatch();

    public abstract int getIndex(char letter);
}

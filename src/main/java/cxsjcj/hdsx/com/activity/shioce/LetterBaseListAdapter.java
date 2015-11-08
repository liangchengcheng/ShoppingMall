package cxsjcj.hdsx.com.activity.shioce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public abstract class LetterBaseListAdapter<T> extends LetterBaseAdapter
{
    private static final String TAG = "LetterBaseListAdapter";
    private static final char ERROR_LETTER = ' ';
    private static final int TYPE_COUNT = 2;
    private static final int TYPE_LETTER = 0;
    private static final int TYPE_CONTAINER = 1;
    protected final List<T> list;
    private final Map<Character, Integer> letterMap;
    public LetterBaseListAdapter()
    {
        list = new ArrayList<T>();
        letterMap = new HashMap<Character, Integer>();
    }
    public LetterBaseListAdapter(T[] dataArray)
    {
        this();
        setContainerList(dataArray);
    }
    public LetterBaseListAdapter(List<T> dataList)
    {
        this();
        setContainerList(dataList);
    }
    protected final void setContainerList(T[] dataArray)
    {
        if(!list.isEmpty())
        {
            list.clear();
        }
        if(!letterMap.isEmpty())
        {
            letterMap.clear();
        }
        
        char letter = ERROR_LETTER;
        int index = 0;
        for(int i=0; i<dataArray.length; i++)
        {
            T t = dataArray[i];
            
            char l = getHeaderLetter(t);
            
            if(letter != l && l != ERROR_LETTER)
            {
                letter = l;

                T tl = create(letter);
                if(tl != null)
                {
                    list.add(tl);
                }
                letterMap.put(letter, index);
                index++;
            }
            list.add(t);
            index++;
        }
    }

    protected final void setContainerList(List<T> dataList)
    {
        if(!list.isEmpty())
        {
            list.clear();
        }
        if(!letterMap.isEmpty())
        {
            letterMap.clear();
        }
        
        char letter = ' ';
        int index = 0;
        for(int i=0; i<dataList.size(); i++)
        {
            T t = dataList.get(i);
            
            char l = getHeaderLetter(t);
            
            if(letter != l && l != ERROR_LETTER)
            {
                letter = l;
                T tl = create(letter);
                if(tl != null)
                {
                    list.add(tl);
                }
                letterMap.put(letter, index);
                index++;
            }
            list.add(t);
            index++;
        }
    }
    private char getHeaderLetter(T t)
    {
        String str = getItemString(t);
        if(TextUtils.isEmpty(str))
        {
            Log.e(TAG, "item string empty in " + t.toString());
            return ERROR_LETTER;
        }
        char l;

        char firstChar = str.charAt(0);
        if(firstChar == HEADER || firstChar == FOOTER || LetterUtil.isLetter(firstChar))
        {
            l = firstChar;
        }
        else
        {
            String[] letterArray = LetterUtil.getFirstPinyin(firstChar);

            if(letterArray != null && letterArray.length > 0)
            {
                l = letterArray[0].charAt(0);
            }
            else
            {
                Log.e(TAG, firstChar + " turn to letter fail, " + t.toString());
                return ERROR_LETTER;
            }
        }
        if(l >= 'a')
        {
            l = (char) (l - 32);
        }
        return l;
    }

    @Override
    public final int getCount()
    {
        return list.size();
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent)
    {
        if(getItemViewType(position) == TYPE_LETTER)
        {
            return getLetterView(position, convertView, parent);
        }
        return getContainerView(position, convertView, parent);
    }
    
    @Override
    public final int getItemViewType(int position)
    {
        if(isLetter(list.get(position)))
        {
            return TYPE_LETTER;
        }
        return TYPE_CONTAINER;
    }

    @Override
    public final int getViewTypeCount()
    {
        return TYPE_COUNT;
    }

    @Override
    public boolean hideLetterNotMatch()
    {
        return false;
    }
    
    @Override
    public final int getIndex(char letter)
    {
        Integer index = letterMap.get(letter);
        if(index == null)
        {
            return -1;
        }
        return index;
    }

    public abstract String getItemString(T t);

    public abstract T create(char letter);

    public abstract boolean isLetter(T t);

    public abstract View getLetterView(int position, View convertView, ViewGroup parent);

    public abstract View getContainerView(int position, View convertView, ViewGroup parent);
}

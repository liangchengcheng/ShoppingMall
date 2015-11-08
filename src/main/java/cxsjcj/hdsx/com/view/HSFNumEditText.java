package cxsjcj.hdsx.com.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cxsjcj.hdsx.com.ipl.NumChangedListener;
import cxsjcj.hdsx.com.myapplication.R;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:   2015年10月25日14:57:36
 * Description: 对购物车的数量的增加和减少的控件的简单的封装
 */
public class HSFNumEditText extends LinearLayout {
    private ImageView numadd = null;
    private ImageView numdes = null;
    private EditText numedit = null;
    private NumChangedListener numChangedListener = null;

   //数字改变的监听器
    public void setNumChangedListener(NumChangedListener numChangedListener) {
        this.numChangedListener = numChangedListener;
    }

    int maxnum = 999999;

    public void setNumEditClickListener(OnClickListener clickListener) {
        numedit.setOnClickListener(clickListener);
    }

    public boolean isEmpty() {
        return ("".equals(numedit.getText().toString().trim()) || numedit
                .getText().toString().trim().length() == 0);
    }

   //设置可用购物的最大值是9999
    public void setMaxnum(int maxnum) {
        this.maxnum = maxnum;
    }

    public int getNum() {
        String num = numedit.getText().toString().trim();
        return ("".equals(num) || num.length() == 0) ? 1 : Integer
                .parseInt(num);
    }

    //设置值
    public void setNum(int num) {
        if (num <= 0) {
            num = 1;
        } else if (num > maxnum) {
            num = maxnum;
        }
        numedit.setText(String.valueOf(num));
    }

    public HSFNumEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public HSFNumEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public HSFNumEditText(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onFinishInflate() {
        // TODO Auto-generated method stub
        super.onFinishInflate();
        initView();

    }

    private void initView() {
        // TODO Auto-generated method stub
        numadd = (ImageView) findViewById(R.id.iv_product_num_add);
        numdes = (ImageView) findViewById(R.id.iv_product_num_des);
        numedit = (EditText) findViewById(R.id.et_product_num);

        numadd.setOnClickListener(clickListener);
        numdes.setOnClickListener(clickListener);
        numdes.setEnabled(false);
        numedit.addTextChangedListener(textWatcher);
        numedit.setSelection(numedit.length());
    }

    OnClickListener clickListener = new OnClickListener() {

        @Override
        public void onClick(View view) {
            // TODO Auto-generated method stub
            String numstr = numedit.getText().toString().trim();
            int num = ("".equals(numstr) || numstr.length() == 0) ? 0 : Integer
                    .parseInt(numstr);
            numedit.clearFocus();
            if (view == numadd) {
                numadd.requestFocus();
                numedit.setText(String.valueOf(num + 1));
            } else if (view == numdes) {
                numadd.requestFocus();
                numedit.setText(String.valueOf(num - 1));
            }
        }
    };

    TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            String num = s.toString().trim();
            if ("1".equals(num)) {
                numedit.setSelection(numedit.length());
                numdes.setEnabled(false);
            }
            if ("".equals(num) || num.length() == 0) {
                numdes.setEnabled(false);
            } else {
                numedit.setSelection(numedit.length());
                if (Integer.parseInt(num) == 0) {
                    numdes.setEnabled(false);
                    numedit.setText("1");
                }
                if (Integer.parseInt(num) > 1) {
                    numdes.setEnabled(true);
                }
                if (Integer.parseInt(num) == maxnum) {
                    numadd.setEnabled(false);
                } else if (Integer.parseInt(num) < maxnum) {
                    numadd.setEnabled(true);
                } else if (Integer.parseInt(num) > maxnum) {
                    numadd.setEnabled(false);
                    numedit.setText(String.valueOf(maxnum));
                }
            }

            if (numChangedListener != null) {
                if ("".equals(num) || num.length() == 0) {
                    numChangedListener.numchanged(1);
                } else {
                    if ((Integer.parseInt(num) == 0)) {
                        numChangedListener.numchanged(1);
                    } else if (Integer.parseInt(num) > maxnum) {
                        numChangedListener.numchanged(maxnum);
                    } else {
                        numChangedListener.numchanged(Integer.parseInt(num));
                    }
                }
            }
        }
    };

}

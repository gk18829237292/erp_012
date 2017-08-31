package com.gk.erp012.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

import com.gk.erp012.R;


/**
 * Created by ke.gao on 2017/7/27.
 */

public class HRSwitch extends android.support.v7.widget.AppCompatButton {

    private boolean isChecked=false;//默认非选中
    private static final int DEFAULT_BACK = -1;
    private int res_back_check,res_back_uncheck;
    private String txt_check,txt_uncheck;
    public HRSwitch(Context context) {
        super(context);
    }

    public HRSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributeSet(attrs);
    }

    public HRSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributeSet(attrs);
    }

    public void setAttributeSet(AttributeSet attrs){
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.HRSwitch);
        res_back_check = a.getResourceId(R.styleable.HRSwitch_src_checked,DEFAULT_BACK);
        res_back_uncheck = a.getResourceId(R.styleable.HRSwitch_src_unchecked,DEFAULT_BACK);
        txt_check = a.getString(R.styleable.HRSwitch_txt_checked);
        txt_uncheck =a.getString(R.styleable.HRSwitch_txt_unchecked);
        isChecked = a.getBoolean(R.styleable.HRSwitch_checked,false);
        setImageResource();
        a.recycle();
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
    }

    public void changeStatue(){
        isChecked=!isChecked;
        setImageResource();
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        setImageResource();
    }

    public void setImageResource(){
        if(isChecked){
            setText(txt_check);
            setBackgroundResource(res_back_check);
        }else{
            setText(txt_uncheck);
            setBackgroundResource(res_back_uncheck);
        }
    }


}

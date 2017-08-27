package com.gk.erp012.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by ke.gao on 2017/8/27.
 */

public class NoScroolGridView extends GridView {
    public NoScroolGridView(Context context) {
        super(context);
    }

    public NoScroolGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScroolGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //不出现滚动条
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST)+10;
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

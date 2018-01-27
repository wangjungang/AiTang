package com.example.administrator.aitang.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;


/**
 * Created by Administrator on 2017/7/7.
 */

public class MyListView extends ListView {
    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxHeight = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);// AT_MOST表示能扩展多高就扩展多高
        super.onMeasure(widthMeasureSpec, maxHeight);
    }
}

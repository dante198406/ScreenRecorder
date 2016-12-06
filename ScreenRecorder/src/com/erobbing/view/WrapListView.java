package com.erobbing.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by zhangzhaolei on 2016/11/24.
 */

public class WrapListView extends ListView {
    private int mWidth = 0;

    public WrapListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WrapListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // Override onMeasure method, to less width
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = getMeasuredHeight();
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            int childWidth = getChildAt(i).getMeasuredWidth();
            mWidth = Math.max(mWidth, childWidth);
        }

        setMeasuredDimension(mWidth, height);
    }

    /**
     * default wrap_content
     *
     * @param width
     */
    protected void setListWidth(int width) {
        mWidth = width;
        System.out.println("setWidth");
    }
}

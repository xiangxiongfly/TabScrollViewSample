package com.example.tabscrollviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class TabScrollView extends ScrollView {
    public TabScrollView(Context context) {
        this(context, null);
    }

    public TabScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mTabScrollViewListener != null) {
            mTabScrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    public interface TabScrollViewListener {
        void onScrollChanged(TabScrollView tabScrollView, int l, int t, int oldl, int oldt);
    }

    private TabScrollViewListener mTabScrollViewListener;

    public void setTabScrollViewListener(TabScrollViewListener tabScrollViewListener) {
        mTabScrollViewListener = tabScrollViewListener;
    }
}

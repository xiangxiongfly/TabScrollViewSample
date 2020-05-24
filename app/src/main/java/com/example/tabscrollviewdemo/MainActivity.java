package com.example.tabscrollviewdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TabScrollView.TabScrollViewListener {

    private TabLayout tabLayout;
    private TabScrollView tabScrollView;
    private LinearLayout container;
    private ArrayList<View> childViews;
    private int secondScrollDistance;
    private int thirdScrollDistance;
    private int fourthScrollDistance;
    private boolean isScrollDistance = false;
    private boolean isHandleTab = false;//标识是点击Tab还是滑动ScrollView
    private View intercept;
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initView() {
        tabLayout = findViewById(R.id.tabLayout);
        tabScrollView = findViewById(R.id.tabScrollView);
        container = findViewById(R.id.container);
        intercept = findViewById(R.id.intercept);

        childViews = new ArrayList<>();
        int childCount = container.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                tabLayout.addTab(tabLayout.newTab().setText("tab" + (i + 1)));
                childViews.add(container.getChildAt(i));
            }
        }
    }

    private void initListener() {
        intercept.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isHandleTab = true;//让Tab处理滑动
                return false;
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentPosition = tab.getPosition();
                if (!isHandleTab) {
                    return;
                }
                switch (currentPosition) {
                    case 0:
                        tabScrollView.smoothScrollTo(0, 0);
                        break;
                    case 1:
                        tabScrollView.smoothScrollTo(0, secondScrollDistance);
                        break;
                    case 2:
                        tabScrollView.smoothScrollTo(0, thirdScrollDistance);
                        break;
                    case 3:
                        tabScrollView.smoothScrollTo(0, fourthScrollDistance);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabScrollView.setTabScrollViewListener(this);
        tabScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isHandleTab = false;//让ScrollView处理滑动
                return false;
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isScrollDistance) {
            isScrollDistance = true;
            //获取各子View位于屏幕的坐标
            int[] firstLocationOnScreen = new int[2];
            int[] secondLocationOnScreen = new int[2];
            int[] thirdLocationOnScreen = new int[2];
            int[] fourthLocationOnScreen = new int[2];
            childViews.get(0).getLocationInWindow(firstLocationOnScreen);
            childViews.get(1).getLocationInWindow(secondLocationOnScreen);
            childViews.get(2).getLocationInWindow(thirdLocationOnScreen);
            childViews.get(3).getLocationInWindow(fourthLocationOnScreen);

            Log.e("TAG", "第一层距离屏幕距离：" + firstLocationOnScreen[1]);
            Log.e("TAG", "第二层距离屏幕距离：" + secondLocationOnScreen[1]);
            Log.e("TAG", "第三层距离屏幕距离：" + thirdLocationOnScreen[1]);
            Log.e("TAG", "第四层距离屏幕距离：" + fourthLocationOnScreen[1]);

            //计算各子View到顶部的距离
            secondScrollDistance = secondLocationOnScreen[1] - firstLocationOnScreen[1];
            thirdScrollDistance = thirdLocationOnScreen[1] - firstLocationOnScreen[1];
            fourthScrollDistance = fourthLocationOnScreen[1] - firstLocationOnScreen[1];
        }
    }

    @Override
    public void onScrollChanged(TabScrollView tabScrollView, int l, int t, int oldl, int oldt) {
        if (isHandleTab) {
            return;
        }

        if (t < secondScrollDistance) {
            if (currentPosition != 0) {
                tabLayout.getTabAt(0).select();
            }
        } else if (t < thirdScrollDistance) {
            if (currentPosition != 1) {
                tabLayout.getTabAt(1).select();
            }
        } else if (t < fourthScrollDistance) {
            if (currentPosition != 2) {
                tabLayout.getTabAt(2).select();
            }
        } else {
            if (currentPosition != 3) {
                tabLayout.getTabAt(3).select();
            }
        }

    }
}



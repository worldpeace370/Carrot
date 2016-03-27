package com.lebron.carrot.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wuxiangkun on 2016/3/19.
 * Contacts by wuxiangkun@live.com
 */
public class MyNestedScrollView extends NestedScrollView {
    public MyNestedScrollView(Context context) {
        super(context);
    }

    public MyNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 为了支持ListView下拉刷新，重写了该方法.但是往上推的时候ToolBar隐藏效果没有了
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}

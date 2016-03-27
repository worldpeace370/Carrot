package com.lebron.carrot.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**自定义ListView为了避免与ScrollView的滑动冲突
 * 重写onMeasure（）方法，赋expandSpec新值
 * Created by wuxiangkun on 2016/3/18.
 * Contacts by wuxiangkun@live.com
 */
public class MyListView extends ListView{
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

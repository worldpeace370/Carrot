package com.lebron.carrot.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by wuxiangkun on 2016/3/20.
 * Contacts by wuxiangkun@live.com
 */
public abstract class FatherActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initView();
    }

    /**
     * 查找view
     */
    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    /**
     * 加载布局
     *
     * @return
     */
    protected abstract int getContentView();

    /**
     * 初始化view
     */
    protected abstract void initView();
}

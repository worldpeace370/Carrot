package com.lebron.carrot.manager;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by wuxiangkun on 2016/3/18.
 * Contacts by wuxiangkun@live.com
 */
public class MyApplication extends Application{
    private static RequestQueue requestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getRequestQueue(){
        return requestQueue;
    }
}

package com.lebron.carrot.imple;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lebron.carrot.bean.Temperature;
import com.lebron.carrot.interfaces.GetDataListener;
import com.lebron.carrot.interfaces.Model;
import com.lebron.carrot.manager.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiangkun on 2016/3/18.
 * Contacts by wuxiangkun@live.com
 */
public class ModelVolley implements Model{
    @Override
    public void getDataFromServer(String urlStr, final GetDataListener listener) {
        StringRequest request = new StringRequest(urlStr, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
            }
        });
        MyApplication.getRequestQueue().add(request);
    }

    public List<Temperature> getListDataFromJSon(String string){
        List<Temperature> list = JSONArray.parseArray(string, Temperature.class);
        if(list != null){
            List<Temperature> tempList = new ArrayList<>();
            int size = list.size();
            for (int i = size-1; i >= 0; i--) {
                tempList.add(list.get(i));
            }
            return tempList;
        }else {
            return null;
        }
    }
}

package com.lebron.carrot.imple;

import com.lebron.carrot.interfaces.GetDataListener;
import com.lebron.carrot.interfaces.Model;
import com.lebron.carrot.utils.OkHttpUtils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by wuxiangkun on 2016/3/17.
 * Contacts by wuxiangkun@live.com
 */
public class ModelOkHttp implements Model{
    @Override
    public void getDataFromServer(String urlStr, final GetDataListener listener) {
        //1,创建okHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2,创建一个Request
        Request request = new Request.Builder().url(urlStr).build();
        //3,根据request创建call
        Call call = okHttpClient.newCall(request);
        //4，请求加入调度,异步的方式去执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onError();
            }
            //onResponse执行的线程并不是UI线程,通过listener接口回调时，不能再listener方法中操作UI，因为同属于子线程
            @Override
            public void onResponse(Response response) throws IOException {
                String jsonStr = response.body().string();
                listener.onSuccess(jsonStr);
            }
        });

    }
}

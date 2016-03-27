package com.lebron.carrot.utils;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wuxiangkun on 2016/3/18.
 * Contacts by wuxiangkun@live.com
 */
public class OkHttpUtils{
    private static final OkHttpClient okHttpClinet = new OkHttpClient();

    /**
     * 根据url获得Request对象
     * @param urlString
     * @return
     */
    private static Request getRequestFromUrl(String urlString){
        Request request = new Request.Builder()
                .url(urlString)
                .build();
        return request;
    }

    /**
     * 根据url获得Response对象
     * @param urlString
     * @return
     * @throws IOException
     */
    private static Response getResponseFromUrl(String urlString) throws IOException {
        Request request = getRequestFromUrl(urlString);
        Response response = okHttpClinet.newCall(request).execute();
        return response;
    }

    /**
     * 根据url获得ResponseBody对象
     * @param urlString
     * @return
     * @throws IOException
     */
    private static ResponseBody getResponseBodyFromUrl(String urlString) throws IOException {
        Response response = getResponseFromUrl(urlString);
        if (response.isSuccessful()){
            return response.body();
        }
        return null;
    }

    /**需要在主线程中开启子线程来调用该方法，然后通过Handler 更新UI
     * 根据url获得字符串对象
     * @param urlString
     * @return
     * @throws IOException
     */
    public static String loadStringFromUrl(String urlString) throws IOException {
        ResponseBody responseBody = getResponseBodyFromUrl(urlString);
        if (responseBody != null){
            return responseBody.string();
        }
        return null;
    }

    /**需要在主线程中开启子线程来调用该方法，然后通过Handler 更新UI
     * 根据url获得字节数组对象
     * @param urlString
     * @return
     * @throws IOException
     */
    public static byte[] loadByteFromUrl(String urlString) throws IOException {
        ResponseBody responseBody = getResponseBodyFromUrl(urlString);
        if (responseBody != null){
            return responseBody.bytes();
        }
        return null;
    }

    /**需要在主线程中开启子线程来调用该方法，然后通过Handler 更新UI
     * 根据url获得IO流对象
     * @param urlString
     * @return
     * @throws IOException
     */
    public static InputStream loadStreamFromUrl(String urlString) throws IOException {
        ResponseBody responseBody = getResponseBodyFromUrl(urlString);
        if (responseBody != null){
            return responseBody.byteStream();
        }
        return null;
    }
}

package com.lebron.carrot.interfaces;

/**
 * Created by wuxiangkun on 2016/3/17.
 * Contacts by wuxiangkun@live.com
 */
public interface Model {
    void getDataFromServer(String urlStr, GetDataListener listener);
}

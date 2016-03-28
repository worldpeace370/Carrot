package com.lebron.carrot;

import android.test.AndroidTestCase;

/** 这是专门用来测试的Android类,所有的方法以test开头，并为public
 * Created by wuxiangkun on 2016/3/28.
 * Contacts by wuxiangkun@live.com
 */
public class MyTest extends AndroidTestCase{
    public void testAdd(){
        int a=1;
        int b=2;
        int c = a+b;
        assertEquals(c, 4);
    }
}

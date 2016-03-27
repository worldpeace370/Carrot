package com.lebron.carrot.manager;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuxiangkun on 2016/3/17.
 * Contacts by wuxiangkun@live.com
 */
public class MyActivityManager{
    /**
     * 将App中所有的Activity加入LinkedList<Activity>，优于ArrayList
     */
    private List<Activity> activityList = new LinkedList<>();
    /**
     * ActivityManager的管理者,单例模式
     */
    private static MyActivityManager instance;

    /**
     * 私有的构造方法
     */
    private MyActivityManager(){

    }

    /**
     * 静态方法得到ActivityManager的单例
     * @return ActivityManager
     */
    public static MyActivityManager getInstance(){
        if (null == instance) {
            instance = new MyActivityManager();
        }
        return instance;
    }

    /**
     * 往集合里面添加活动
     * @param activity
     */
    public void addActivity(Activity activity){
        activityList.add(activity);
    }

    /**
     * 执行onDestroy()方法的时候执行
     * @param activity
     */
    public void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    /**
     * 程序退出时,遍历activityList,销毁所有的活动
     */
    public void exit(){
        MyApplication.getRequestQueue().stop();
        for (Activity activity:activityList) {
            if (!activity.isFinishing() && activity!=null) {
                activity.finish();
            }
        }
        int id = android.os.Process.myPid();
        if (id != 0) {
            android.os.Process.killProcess(id);
        }
    }
}

package com.lebron.carrot.bean;

/**
 * Created by wuxiangkun on 2016/3/20.
 * Contacts by wuxiangkun@live.com
 */
public class Temperature {
    private int value;
    private String create_time;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "value: "+ value + ";" + "create_time:" + create_time;
    }
}

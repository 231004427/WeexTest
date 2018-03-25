package com.sunlin.weextest.common;

/**
 * Created by sunlin on 2018/3/25.
 */

public class NameValuePair {
    private String mName;
    private String mValue;
    public NameValuePair(String name,String value){
        mName=name;
        mValue=value;
    }
    public String getName()
    {
        return mName;
    }
    public String getValue(){
        return mValue;
    }
}

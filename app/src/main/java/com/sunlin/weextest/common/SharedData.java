package com.sunlin.weextest.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sunlin on 2018/3/25.
 */

public class SharedData {
    public static void saveValue(Context context, String key,String value){
        //保存用户数据
        SharedPreferences sp = context.getSharedPreferences("Weextest", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,value);
        editor.commit();
    }
    public static String getValue(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences("Weextest", Context.MODE_PRIVATE);
        return  sp.getString(key,null);
    }
}

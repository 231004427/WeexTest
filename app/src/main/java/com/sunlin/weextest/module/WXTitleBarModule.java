package com.sunlin.weextest.module;

import android.app.Activity;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.sunlin.weextest.WeexActivity;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.util.Map;

/**
 * Created by sunlin on 2018/3/23.
 */

public class WXTitleBarModule extends WXModule {
    @JSMethod(uiThread = false)
    public void push(String param,JSCallback callback){

        Map<String, String> maps = (Map<String, String>) JSON.parse(param);
        String url= maps.get("url");
        String title= maps.get("title");
        String rTitle= maps.get("rTitle");
        String rColor= maps.get("rColor");
        String rTag= maps.get("rTag");
        String rImg= maps.get("rImg");


        Intent intent = new Intent(mWXSDKInstance.getContext(), WeexActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("title",title);
        intent.putExtra("rTitle",rTitle);
        intent.putExtra("rColor",rColor);
        intent.putExtra("rTag",rTag);
        intent.putExtra("rImg",rImg);
        mWXSDKInstance.getContext().startActivity(intent);
    }
    @JSMethod(uiThread = false)
    public void pop(String param){

        Activity activity=(Activity)mWXSDKInstance.getContext();
        activity.finish();
    }
}

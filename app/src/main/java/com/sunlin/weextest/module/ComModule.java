package com.sunlin.weextest.module;

import android.content.Intent;
import android.os.Build;

import com.alibaba.fastjson.JSON;
import com.sunlin.weextest.MainActivity;
import com.sunlin.weextest.WeexActivity;
import com.sunlin.weextest.common.LogC;
import com.sunlin.weextest.common.ScreenUtil;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunlin on 2018/3/22.
 */
public class ComModule extends WXModule {
    @JSMethod(uiThread = false)
    public void printLog(String msg,JSCallback callback){
        LogC.write(msg,"ComModule");

        Map<String, String> infos = new HashMap<>();
        infos.put("board", Build.BOARD);
        infos.put("brand", Build.BRAND);
        infos.put("device", Build.DEVICE);
        infos.put("model", Build.MODEL);
        callback.invoke(infos);
    }
    //刷新
    @JSMethod(uiThread = false)
    public void reload(){
        WeexActivity weexActivity=(WeexActivity)mWXSDKInstance.getContext();
        weexActivity.render();
    }
    @JSMethod(uiThread = false)
    public void getHeight(String param,JSCallback callback){
        WeexActivity weexActivity=(WeexActivity)mWXSDKInstance.getContext();
        float barHeight=44*ScreenUtil.getScreenDensity(weexActivity);
        int statusHeight=ScreenUtil.getStatusHeight(weexActivity);
        int topHeight=statusHeight+(int)barHeight;
        Map<String, String> infos = new HashMap<>();
        infos.put("barHeight", String.valueOf((int)barHeight));
        infos.put("statusHeight", String.valueOf(statusHeight));
        infos.put("topHeight", String.valueOf(topHeight));
        callback.invoke(infos);
    }
    @JSMethod(uiThread = false)
    public void getToken(String param,JSCallback callback){

        //178b583ea44264c38df1e9ebe2900868
        Map<String, String> infos = new HashMap<>();
        infos.put("token", "178b583ea44264c38df1e9ebe2900868");
        callback.invoke(infos);
    }
    @JSMethod(uiThread = false)
    public void goActivity(String param,JSCallback callback){
        //type,返回订单中心
        Map<String, String> maps = (Map<String, String>) JSON.parse(param);
        String type= maps.get("type");
        if(type.equals("1") && type!=null){
            Intent intent = new Intent(mWXSDKInstance.getContext(), MainActivity.class);
            mWXSDKInstance.getContext().startActivity(intent);

        }
    }
}

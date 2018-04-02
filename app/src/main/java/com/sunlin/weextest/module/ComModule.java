package com.sunlin.weextest.module;

import android.os.Build;
import com.sunlin.weextest.WeexActivity;
import com.sunlin.weextest.common.LogC;
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
}

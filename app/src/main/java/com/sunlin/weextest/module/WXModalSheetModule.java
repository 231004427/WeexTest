package com.sunlin.weextest.module;

import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.sunlin.weextest.WeexActivity;
import com.sunlin.weextest.common.dialog.ConfirmDialog;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.util.HashMap;
import java.util.Map;

public class WXModalSheetModule extends WXModule {

    //提示框
    @JSMethod(uiThread = true)
    public void toast(Map<String, Object> options){

        Object message = options.get("message");
        Object duration = options.get("duration");
        Toast.makeText(mWXSDKInstance.getContext(), String.valueOf(message), Toast.LENGTH_SHORT).show();
    }
    //确认框
    @JSMethod(uiThread = true)
    public void alert(Map<String, Object> options, final JSCallback callback){
        Object title = options.get("title");//提示框标题，空不显示标题
        Object message = options.get("message");//提示框内容，必须
        Object okTitle = options.get("okTitle");//确认按钮文案，必须
        Object cancelTitle = options.get("cancelTitle");//取消按钮，空不显示
        WeexActivity weexActivity=(WeexActivity)mWXSDKInstance.getContext();
        final ConfirmDialog dialog=new ConfirmDialog();
        dialog.setStr(String.valueOf(title),String.valueOf(okTitle),String.valueOf(cancelTitle),String.valueOf(message));
        dialog.show(weexActivity.getFragmentManager(),"dialog");
        dialog.setOnClickListener(new ConfirmDialog.OnClickListener() {
            @Override
            public void onClick(int type) {
                if(callback != null){
                Map<String, Object> infos = new HashMap<>();
                infos.put("result", type);//返回结果，1，确认按钮，2，取消按钮
                callback.invoke(infos);
                }
            }
        });

    }
}

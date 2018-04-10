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

    @JSMethod(uiThread = true)
    public void toast(Map<String, Object> options){

        Object message = options.get("message");
        Object duration = options.get("duration");
        Toast.makeText(mWXSDKInstance.getContext(), String.valueOf(message), Toast.LENGTH_SHORT).show();
    }
    @JSMethod(uiThread = true)
    public void alert(Map<String, Object> options, final JSCallback callback){
        Object title = options.get("title");
        Object message = options.get("message");
        Object okTitle = options.get("okTitle");
        Object cancelTitle = options.get("cancelTitle");
        WeexActivity weexActivity=(WeexActivity)mWXSDKInstance.getContext();
        final ConfirmDialog dialog=new ConfirmDialog();
        dialog.setStr(String.valueOf(title),String.valueOf(okTitle),String.valueOf(cancelTitle),String.valueOf(message));
        dialog.show(weexActivity.getFragmentManager(),"dialog");
        dialog.setOnClickListener(new ConfirmDialog.OnClickListener() {
            @Override
            public void onClick(int type) {
                Map<String, Object> infos = new HashMap<>();
                infos.put("result", type);
                callback.invoke(infos);
            }
        });

    }
}

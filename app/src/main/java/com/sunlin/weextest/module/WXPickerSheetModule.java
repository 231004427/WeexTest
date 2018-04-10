package com.sunlin.weextest.module;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import com.sunlin.weextest.MainActivity;
import com.sunlin.weextest.WeexActivity;
import com.sunlin.weextest.common.picker.popwindow.SelectPickerPopWin;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WXPickerSheetModule extends WXSDKEngine.DestroyableModule {

    @JSMethod
    public void pick(Map<String, Object> options, final JSCallback callback) {

        Object index = options.get("index");
        Object items = options.get("items");
        List<String> itemList=new ArrayList();
        for (String map : (List<String>)items) {
            itemList.add(map);
        }

        WeexActivity weexActivity=(WeexActivity)mWXSDKInstance.getContext();

        int bottom=weexActivity.getBackButtonHight();


        SelectPickerPopWin pickerPopWin = new SelectPickerPopWin.Builder(weexActivity, new SelectPickerPopWin.OnSelectPickedListener() {
            @Override
            public void onSelectPickCompleted(int index, String valueDesc) {
                Map<String, Object> infos = new HashMap<>();
                infos.put("result", "success");
                infos.put("data", index);
                callback.invoke(infos);
            }
        }).itemsBuild(itemList)
                .textConfirm("确认") //text of confirm button
                .textCancel("取消") //text of cancel button
                .btnTextSize(16) // button text size
                .viewTextSize(25) // pick view text size
                .bottomConfirm(bottom)
                .colorCancel(Color.parseColor("#999999")) //color of cancel button
                .colorConfirm(Color.parseColor("#009900"))//color of confirm button
                .valueChose("Amber") //
                .build();
        pickerPopWin.showPopWin(weexActivity);
    }
    @Override
    public void destroy() {
    }
}

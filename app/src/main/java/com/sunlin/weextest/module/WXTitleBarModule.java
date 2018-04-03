package com.sunlin.weextest.module;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.sunlin.weextest.MyActivtiyToolBar;
import com.sunlin.weextest.R;
import com.sunlin.weextest.WeexActivity;
import com.sunlin.weextest.common.LogC;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunlin on 2018/3/23.
 */

public class WXTitleBarModule extends WXModule {

    @JSMethod(uiThread = false)
    public void setTitle(String param,JSCallback callback){
        Map<String, String> maps = (Map<String, String>) JSON.parse(param);
        //设置标题
        String title= maps.get("title");
        MyActivtiyToolBar activity=(MyActivtiyToolBar)mWXSDKInstance.getContext();
        activity.toolText.setText(title);

        Map<String, String> infos = new HashMap<>();
        infos.put("result", "ok");
        callback.invoke(infos);
    }

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
    public void setNavBarRightItem(String param,JSCallback callback){
        Map<String, String> maps = (Map<String, String>) JSON.parse(param);
        String rTitle= maps.get("title");
        String rColor= maps.get("color");
        String rTag= maps.get("tag");
        String rImg= maps.get("img");

        //右侧按钮设置
        MyActivtiyToolBar activity=(MyActivtiyToolBar)mWXSDKInstance.getContext();
        Boolean isSet=true;
        if(rTitle.isEmpty() && rImg.isEmpty()){
            isSet=false;
        }
        if(rImg.isEmpty()){
            activity.toolSet.setText(rTitle);
            String tempColor=rColor.isEmpty() ? "#000000":rColor;
            activity.toolSet.setTextColor(Color.parseColor(tempColor));
        }else{
            activity.toolSet.setBackgroundResource(getResourceByReflect(rImg));
        }
        int tagBtn=rTag.isEmpty()?0:Integer.parseInt(rTag);
        activity.toolSet.setTag(tagBtn);

        if(isSet){
            activity.ToolbarSetListense(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyActivtiyToolBar activity=(MyActivtiyToolBar)mWXSDKInstance.getContext();
                    Map data = new HashMap<String,Object>();
                    data.put("tag", activity.toolSet.getTag());
                    mWXSDKInstance.fireEvent("_root","navclick",data,null);
                    mWXSDKInstance.fireGlobalEventCallback("navclickEvent",data);
                }
            });
        }
        Map<String, String> infos = new HashMap<>();
        infos.put("result", "ok");
        callback.invoke(infos);
    }
    private int  getResourceByReflect(String imageName){
        Class drawable = R.drawable.class;
        Field field = null ;
        int r_id;
        try {
            field = drawable.getField(imageName);
            r_id = field.getInt(field.getName());
        } catch (Exception e) {
            r_id = R.drawable.ios_app;
            LogC.e("ERROR", "PICTURE NOT　FOUND！");
        }
        return r_id;
    }
}

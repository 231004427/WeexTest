package com.sunlin.weextest.common;

import android.app.Application;
import com.sunlin.weextest.module.ComModule;
import com.sunlin.weextest.module.WXActionSheetModule;
import com.sunlin.weextest.module.WXTitleBarModule;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;

/**
 * Created by sunlin on 2018/3/21.
 */

public class WeexApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        InitConfig config = new InitConfig.Builder().setImgAdapter(new WXImageAdapter()).build();
        try {

            WXSDKEngine.registerModule("ComModule",ComModule.class);
            WXSDKEngine.registerModule("titleBar",WXTitleBarModule.class);
            WXSDKEngine.registerModule("actionSheet", WXActionSheetModule.class);
            WXSDKEngine.registerComponent("richtext", RichText.class, false);
        } catch (WXException e) {
            e.printStackTrace();
        }
        WXSDKEngine.initialize(this, config);
    }

}

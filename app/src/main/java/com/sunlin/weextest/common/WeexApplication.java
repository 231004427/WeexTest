package com.sunlin.weextest.common;

import android.app.Application;
import com.sunlin.weextest.module.ComModule;
import com.sunlin.weextest.module.WXActionSheetModule;
import com.sunlin.weextest.module.WXModalSheetModule;
import com.sunlin.weextest.module.WXPickerSheetModule;
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
        try {
            InitConfig config = new InitConfig.Builder().setImgAdapter(new WXImageAdapter()).build();
            WXSDKEngine.registerModule("ComModule",ComModule.class);
            WXSDKEngine.registerModule("titleBar",WXTitleBarModule.class);
            WXSDKEngine.registerModule("actionSheet", WXActionSheetModule.class);
            //选择框
            WXSDKEngine.registerModule("pickerSheet", WXPickerSheetModule.class);
            //提示框
            WXSDKEngine.registerModule("modalSheet", WXModalSheetModule.class);
            WXSDKEngine.registerComponent("richtext", RichText.class, false);
            WXSDKEngine.initialize(this, config);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }

}

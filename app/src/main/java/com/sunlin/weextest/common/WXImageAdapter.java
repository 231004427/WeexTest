package com.sunlin.weextest.common;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.sunlin.weextest.R;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

import java.lang.reflect.Field;

/**
 * Created by sunlin on 2018/3/21.
 */

public class WXImageAdapter implements IWXImgLoaderAdapter {
    public WXImageAdapter() {
    }
    @Override
    public void setImage(final String url, final ImageView view, WXImageQuality quality, WXImageStrategy strategy) {
        WXSDKManager.getInstance().postOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(url)) {
                    view.setImageBitmap(null);
                    return;
                }
                String tempUrl = url;
                if (url.startsWith("//")) {
                    tempUrl = "http:" + url;
                }
                if (view.getLayoutParams().width <= 0 || view.getLayoutParams().height <= 0) {
                    return;
                }

                if(tempUrl.indexOf("asserts:")>=0){
                    Uri urlObj=Uri.parse(url);
                    Picasso.with(WXEnvironment.getApplication())
                            .load(getResourceByReflect(urlObj.getHost()))
                            .into(view);
                }else{
                    Picasso.with(WXEnvironment.getApplication())
                            .load(tempUrl)
                            .into(view);
                }


            }
        },0);
    }
    public int getResourceByReflect(String imageName){
        Class drawable  =  R.drawable.class;
        Field field = null;
        int r_id ;
        try {
            field = drawable.getField(imageName);
            r_id = field.getInt(field.getName());
        } catch (Exception e) {
            r_id=R.drawable.ios_app;
            Log.e("ERROR", "PICTURE NOT　FOUND！");
        }
        return r_id;
    }
}

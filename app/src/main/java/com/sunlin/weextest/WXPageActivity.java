package com.sunlin.weextest;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sunlin.weextest.common.HttpTask;
import com.sunlin.weextest.common.RestTask;
import com.sunlin.weextest.common.ScreenUtil;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.RenderContainer;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.ui.component.NestedContainer;
import com.taobao.weex.utils.WXLogUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by sunlin on 2018/3/27.
 */

public class WXPageActivity extends MyActivtiyToolBar implements IWXRenderListener, Handler.Callback, WXSDKInstance.NestedInstanceInterceptor,RestTask.ResponseCallback{

    private static final String TAG = "WXPageActivity";
    public static final String WXPAGE = "wxpage";
    private WXSDKInstance mInstance;
    private Handler mWXHandler;
    private ViewGroup mContainer;
    private String url="http://10.66.48.190:8081/dist/list.weex.js";
    private HashMap mConfigMap = new HashMap<String, Object>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFormat(PixelFormat.TRANSLUCENT);//窗口支持透明
        mContainer = (ViewGroup) findViewById(R.id.container);
        loadWXfromService(url);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_wxpage;
    }

    private void loadWXfromService(final String url) {

        if (mInstance != null) {
            mInstance.destroy();
        }

        //RenderContainer renderContainer = new RenderContainer(this);
        //mContainer.addView(renderContainer);

        mInstance = new WXSDKInstance(this);
        //mInstance.setRenderContainer(renderContainer);
        mInstance.registerRenderListener(this);
       // mInstance.setNestedInstanceInterceptor(this);
        //mInstance.setBundleUrl(url);
        //mInstance.setTrackComponent(true);
        mConfigMap.put("bundleUrl", url);
        mInstance.renderByUrl("WXSample", url, mConfigMap, null,WXRenderStrategy.APPEND_ONCE);
        //HttpTask.Get(url,null,this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mInstance != null) {
            mInstance.onActivityDestroy();
        }
        mContainer = null;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mInstance != null) {
            mInstance.onActivityResume();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mInstance != null) {
            mInstance.onActivityPause();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mInstance != null) {
            mInstance.onActivityStop();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(mInstance!=null){
            mInstance.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mInstance!=null){
            mInstance.onActivityResult(requestCode,resultCode,data);
        }
    }
    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }
    @Override
    public void onViewCreated(WXSDKInstance wxsdkInstance, View view) {

        WXLogUtils.e("into--[onViewCreated]");
        if(view.getParent() == null) {
            mContainer.addView(view);
        }
        mContainer.requestLayout();
        Log.d("WARenderListener", "renderSuccess");
    }

    @Override
    public void onRenderSuccess(WXSDKInstance wxsdkInstance, int i, int i1) {

    }

    @Override
    public void onRefreshSuccess(WXSDKInstance wxsdkInstance, int i, int i1) {

    }

    @Override
    public void onException(WXSDKInstance wxsdkInstance, String s, String s1) {

    }

    @Override
    public void onCreateNestInstance(WXSDKInstance wxsdkInstance, NestedContainer nestedContainer) {
        Log.d(TAG, "Nested Instance created.");

    }

    @Override
    public void onRequestSuccess(String response) {
        Log.i(TAG, "into--[http:onSuccess] url:" + url);

        mConfigMap.put("bundleUrl", url);
        mInstance.render(TAG, response, mConfigMap, null, WXRenderStrategy.APPEND_ASYNC);
    }

    @Override
    public void onRequestError(Exception error) {
        Log.i(TAG, "into--[http:onError]");
    }
}

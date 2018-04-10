package com.sunlin.weextest;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunlin.weextest.common.ScreenUtil;

import java.lang.reflect.Method;

/**
 * Created by sunlin on 2018/3/20.
 */

public abstract class MyActivtiyToolBar extends AppCompatActivity {
    public Toolbar toolbar;
    public TextView toolText;
    public ImageView toolBack;
    public TextView toolSet;

    //初始化导航栏
    public void ToolbarBuild(String title, boolean isBack, boolean isSet)
    {
        toolText.setText(title);
        if(!isSet){toolbar.removeView(toolSet);}else{
        }
        if(!isBack){toolbar.removeView(toolBack);}{
        ToolbarBackListense();}

    }
    //设置按钮事件
    public void ToolbarSetListense(View.OnClickListener l){
        toolSet.setOnClickListener(l);
    }
    //返回按钮事件
    public void ToolbarBackListense(){
        toolBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public int displayHeight(){
        return (int)ScreenUtil.getScreenHeightPixels(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());//把设置布局文件的操作交给继承的子类            android:screenOrientation="portrait"

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolText = (TextView) findViewById(R.id.toolbar_title);
        toolBack=(ImageView)findViewById(R.id.btnBack);
        toolSet=(TextView)findViewById(R.id.btnSet);
        setSupportActionBar(toolbar);
        /*
        //这一行注意！看本文最后的说明！！！！
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }

        getSupportActionBar().hide();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }*/
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            toolbar.setPadding(0, ScreenUtil.getStatusHeight(this),0,0);
            toolbar.getLayoutParams().height+=ScreenUtil.getStatusHeight(this);
        }

        //View decorView = getWindow().getDecorView();
        //int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        //        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY ;
        //decorView.setSystemUiVisibility(uiOptions);
    }

    //获取底部返回键等按键的高度
    public int getBackButtonHight(){
        int result = 0;
        if (hasNavBar(this)) {
            Resources res = this.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
        //Log.i("hhhhhhh",result+"****底部按键高度");
    }
    //检查是否存在虚拟按键
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }
    //检查虚拟按键是否被重写
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }
    /**
     * 返回当前Activity布局文件的id
     *
     * @return
     */
    abstract protected int getLayoutResId();
}

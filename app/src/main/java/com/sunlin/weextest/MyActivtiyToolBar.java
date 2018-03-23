package com.sunlin.weextest;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunlin.weextest.common.ScreenUtil;

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

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY ;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 返回当前Activity布局文件的id
     *
     * @return
     */
    abstract protected int getLayoutResId();
}

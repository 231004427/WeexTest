package com.sunlin.weextest

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import com.sunlin.weextest.common.LogC
import com.taobao.weex.WXSDKInstance
import com.taobao.weex.common.WXRenderStrategy
import com.taobao.weex.IWXRenderListener
import com.taobao.weex.WXSDKEngine
import com.taobao.weex.appfram.navigator.IActivityNavBarSetter
import kotlinx.android.synthetic.main.activity_show.*
import com.alibaba.fastjson.JSON
import com.sunlin.weextest.common.ScreenUtil
import com.taobao.weex.WXSDKManager
import java.lang.reflect.Field
import java.util.*


/**
 * Created by sunlin on 2018/3/20.
 */
class ShowActivity: MyActivtiyToolBar(),IWXRenderListener, IActivityNavBarSetter {
    override fun clearNavBarLeftItem(param: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearNavBarMoreItem(param: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setNavBarLeftItem(param: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setNavBarRightItem(param: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun push(param: String?): Boolean {

        val maps = JSON.parse(param) as Map<*, *>

        if(maps["url"]==null || maps["title"]==null){
            return  false
        }else{
            val intent = Intent(this@ShowActivity, ShowActivity::class.java)
            intent.putExtra("url",maps["url"].toString())
            intent.putExtra("title",maps["title"].toString())
            startActivity(intent)
            return  true
        }
    }
    override fun clearNavBarRightItem(param: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pop(param: String?): Boolean {
        finish()
        return true
    }

    override fun setNavBarTitle(param: String?): Boolean {
        toolText.text = param
        return true
    }

    override fun setNavBarMoreItem(param: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    protected var mWeexInstance: WXSDKInstance? = null
    var urlStr:String=""
    var titleStr:String=""
    var rTitle:String=""
    var rColor:String=""
    var rTag:String=""
    var rImg:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val titleExt= getIntent().getStringExtra("title")
        val urlExt=getIntent().getStringExtra("url")
        val rTitleExt=getIntent().getStringExtra("rTitle")
        val rColorExt=getIntent().getStringExtra("rColor")
        val rTagExt=getIntent().getStringExtra("rTag")
        val rImgExt=getIntent().getStringExtra("rImg")

        urlStr= if(urlExt==null) "" else urlExt
        titleStr= if(titleExt==null) "" else titleExt
        rTitle= if(rTitleExt==null) "" else rTitleExt
        rColor= if(rColorExt==null) "" else rColorExt
        rTag= if(rTagExt==null) "" else rTagExt
        rImg= if(rImgExt==null) "" else rImgExt

        buildToolbar()
        render()

    }
    fun buildToolbar(){
        var isSet=true
        if(rTitle.isEmpty() && rImg.isEmpty()){
            isSet=false
        }
        if(rImg.isEmpty()){
            toolSet.setText(rTitle)
            val tempColor = if (rColor.isEmpty()) "#000000" else rColor
            toolSet.setTextColor(Color.parseColor(tempColor))
        }else{
            toolSet.setBackgroundResource(getResourceByReflect(rImg))
        }
        val tagBtn=if (rTag.isEmpty()) 0 else rTag as? Int
        toolSet.setTag(tagBtn)
        ToolbarBuild(titleStr,true,isSet)

        if(isSet){
            ToolbarSetListense {onclick()}
        }
    }
    fun onclick(){
        val data = HashMap<String,Any>()
        data.put("tag", toolSet.tag)
        mWeexInstance?.fireEvent("_root","navclick",data,null);
    }
    fun getResourceByReflect(imageName: String): Int {
        val drawable = R.drawable::class.java
        var field: Field? = null
        var r_id: Int
        try {
            field = drawable.getField(imageName)
            r_id = field!!.getInt(field.name)
        } catch (e: Exception) {
            r_id = R.drawable.ios_app
            Log.e("ERROR", "PICTURE NOT　FOUND！")
        }

        return r_id
    }
    fun render(){
        if (mWeexInstance != null) {
            mWeexInstance?.destroy()
        }
        mWeexInstance = WXSDKInstance(this)
        WXSDKEngine.setActivityNavBarSetter(this)
        mWeexInstance?.registerRenderListener(this)
        //mWeexInstance?.renderByUrl("WXSample","http://10.66.48.190:8081/dist/index.weex.js",null, null, WXRenderStrategy.APPEND_ASYNC);
        val options = HashMap<String,Any>()
        options.put(WXSDKInstance.BUNDLE_URL, urlStr)
        mWeexInstance?.renderByUrl("WXSample", urlStr, options, null, WXRenderStrategy.APPEND_ONCE)
    }

    override fun getLayoutResId(): Int{
        return  R.layout.activity_show
    }

    override fun onViewCreated(instance: WXSDKInstance, view: View) {
        container.addView(view)
    }

    override fun onRenderSuccess(instance: WXSDKInstance, width: Int, height: Int) {}
    override fun onRefreshSuccess(instance: WXSDKInstance, width: Int, height: Int) {}
    override fun onException(instance: WXSDKInstance, errCode: String, msg: String) {}
    override fun onResume() {
        super.onResume()
        mWeexInstance?.onActivityResume()
    }

    override fun onPause() {
        super.onPause()
        mWeexInstance?.onActivityPause();
    }

    override fun onStop() {
        super.onStop()
        mWeexInstance?.onActivityStop();
    }

    override fun onDestroy() {
        super.onDestroy()
        mWeexInstance?.onActivityDestroy();
    }
}
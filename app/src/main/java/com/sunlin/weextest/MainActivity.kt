package com.sunlin.weextest

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private  var url:String=""
    private  var isCatch:Boolean=true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //

        //
        val sp = this.getSharedPreferences("WEEXTEST", Context.MODE_PRIVATE)
        url=sp.getString("url", "")
        isCatch=sp.getBoolean("isCatch",true)

        if(url.isEmpty()) {
            url = "http://127.0.0.1:8081/dist/index.weex.js"
        }

        editText.setText(url)

        // Example of a call to a native method
        button.setOnClickListener {
           url=editText.getText().toString()
            val intent = Intent(this@MainActivity, WeexActivity::class.java)
            intent.putExtra("url",url)
            intent.putExtra("title","WEEX")
            intent.putExtra("rTitle","刷新")
            intent.putExtra("rImg","save22")

            if(checkBox.isChecked){
                intent.putExtra("isCatch","true")
            }else{
                intent.putExtra("isCatch","false")
            }

            startActivity(intent)

            val sp = this.getSharedPreferences("WEEXTEST", Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString("url", url)
            editor.putBoolean("isCatch",checkBox.isChecked)
            editor.commit()
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}

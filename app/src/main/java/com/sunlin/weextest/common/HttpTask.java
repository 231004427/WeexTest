package com.sunlin.weextest.common;

import java.io.IOException;

/**
 * Created by sunlin on 2017/7/6.
 */

public class HttpTask {
    private static String TAG="HttpTask";

    public static void  Post(
            String jsonStr,
            String url,
            RestTask.ProgressCallback progressCallback,
            RestTask.ResponseCallback responseCallback){
        try{
            //List<NameValuePair> parameters=new ArrayList<NameValuePair>();
            //parameters.add(new NameValuePair("title","Android Recipes"));
            //parameters.add(new NameValuePair("summary","Learn Android Quickly"));
            RestTask postTask=
                    RestUtil.obtainFormPostTaskJson(url,jsonStr);
            postTask.setResponseCallback(responseCallback);
            postTask.setProgressCallback(progressCallback);
            postTask.execute();
        }catch (IOException e){
            LogC.write(e,TAG+":Post");
        }
    }
    //
    public static void Get(String url,
                           RestTask.ProgressCallback progressCallback,
                           RestTask.ResponseCallback responseCallback){
        try{
            RestTask postTask=
                    RestUtil.obtainGetTask(url);
            postTask.setResponseCallback(responseCallback);
            postTask.setProgressCallback(progressCallback);
            postTask.execute();
        }catch(IOException e){
            LogC.write(e,TAG+":Post");
        }
    }
}

package com.mask.mywordbook.util;

import android.util.Log;

import com.google.gson.Gson;
import com.mask.mywordbook.bean.EnglishBean;
import com.mask.mywordbook.bean.PoetryBean;
import com.mask.mywordbook.event.PoetryEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class HttpUtil {

    /**
     * 请求英语语录
     */
    public static void sendOkHttpRequestEnglish(final Callback callback)
    {
        String appid="83734";
        String secret="15cffd4bc0bd4c029393932a69bdf899";//要替换成自己的
        String salt = String.valueOf(System.currentTimeMillis());
        Log.e(TAG, "sendOkHttpRequestEnglish: "+salt );

        final FormBody formBody = new FormBody.Builder()
                .add("showapi_appid",appid)
                .add("showapi_sign",secret)
                .add("showapi_timestamp",salt)
                .add("showapi_res_gzip","0")
                .add("count","10")//每次请求英语语录的数量
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://route.showapi.com/1211-1")
                .post(formBody)

                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void sendOkHttpRequestPoetry(){
        new Thread(new Runnable() {
            @Override
            public void run() {

//                OkHttpClient okHttpClient = new OkHttpClient();
//                Request request = new Request.Builder()
//                        .url("https://api.apiopen.top/singlePoetry")
//                        .build();
                String result = "";
//                try {
//                    Response response = okHttpClient.newCall(request).execute();
//                    String json = response.body().string();
//                    Gson gson = new Gson();
                    //PoetryBean poetryBean = gson.fromJson(json, PoetryBean.class);
                    //result = poetryBean.getResult().getContent()!=null ? poetryBean.getResult().getContent():"西风乱叶溪桥树。秋在黄花羞涩处。";
                    result = "西风乱叶溪桥树。秋在黄花羞涩处。";
                    EventBus.getDefault().post(new PoetryEvent(result));

//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        }).start();

    }

    public static String RequestForHttpToEnglish(String data){
        String appKey ="6e85ed4b6946b08c";
        String query = data;
        String salt = String.valueOf(System.currentTimeMillis());
        String from = "zh-CHS";
        String to = "EN";
        String sign = md5(appKey + query + salt+ "acsI8Stn1tuwjteTJRseRbiCMQk5XPa6");

        FormBody formBody = new FormBody.Builder()
                .add("q",data)
                .add("from",from)
                .add("to",to)
                .add("sign",sign)
                .add("salt",salt)
                .add("appKey",appKey)
                .build();
        Request request = new Request.Builder()
                .url("https://openapi.youdao.com/api")
                .post(formBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "翻译失败";
    }

    public static String RequestForHttpToChain(String data){
        String appKey ="6e85ed4b6946b08c";
        String query = data;
        String salt = String.valueOf(System.currentTimeMillis());
        String from = "EN";
        String to = "zh-CHS";
        String sign = md5(appKey + query + salt+ "acsI8Stn1tuwjteTJRseRbiCMQk5XPa6");

        FormBody formBody = new FormBody.Builder()
                .add("q",data)
                .add("from",from)
                .add("to",to)
                .add("sign",sign)
                .add("salt",salt)
                .add("appKey",appKey)
                .build();
        Request request = new Request.Builder()
                .url("https://openapi.youdao.com/api")
                .post(formBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "翻译失败";
    }

    public static void sendOkHttpRequset(String url,Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 生成32位MD5摘要
     * @param string
     * @return
     */
    public static String md5(String string) {
        if(string == null){
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try{
            byte[] btInput = string.getBytes("utf-8");
            /** 获得MD5摘要算法的 MessageDigest 对象 */
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            /** 使用指定的字节更新摘要 */
            mdInst.update(btInput);
            /** 获得密文 */
            byte[] md = mdInst.digest();
            /** 把密文转换成十六进制的字符串形式 */
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }catch(NoSuchAlgorithmException | UnsupportedEncodingException e){
            return null;
        }
    }
}
